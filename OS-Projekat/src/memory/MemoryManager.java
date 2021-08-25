package memory;

import java.util.ArrayList;
import kernel.Process;
import kernel.ProcessState;

public class MemoryManager {

	public static ArrayList<PartitionMemory> partitionsInRam;

	public MemoryManager() {
		Ram.initialize();
		PartitionMemory.initialize();
		partitionsInRam = new ArrayList<>();
	}

	public int loadProcess(Process process) {
		PartitionMemory partitionMemory = PartitionMemory.getPartitionByProcess(process);
		if (!partitionsInRam.contains(partitionMemory)) { // provjerava da li je vec korsten taj proces
			return loadPartition(new PartitionMemory(process));
		} else {
			return process.getStartAdress();
		}
	}

	public int loadPartition(PartitionMemory partiton) {
		int position = load(partiton.getData());// ucita podatke i vrati poziciju
		if (position != -1) {
			partiton.setPositionInMemory(position);
			partitionsInRam.add(partiton);
		}
		return position;
	}

	public int[] readProcess(Process process) {
		return readPartiton(PartitionMemory.getPartitionByProcess(process));
	}

	public int[] readPartiton(PartitionMemory partition) {
		if (partitionsInRam.contains(partition))
			return readRam(partition.getPositionInMemory(), partition.getSize());
		return null;
	}

	public int[] readRam(int start, int size) {
		int[] data = new int[size];
		for (int i = 0; i < data.length; i++) {
			if (Ram.isOcupied(i + start)) {
				data[i] = Ram.getAt(i + start);
			}
		}
		return data;
	}

	public static boolean removeProcess(Process process) {
		return removePartition(PartitionMemory.getPartitionByProcess(process));
	}

	public static boolean removePartition(PartitionMemory partition) {
		if (partitionsInRam.contains(partition)) {
			Ram.removeSequence(partition.getPositionInMemory(), partition.getSize());
			partition.setPositionInMemory(-1);
			partitionsInRam.remove(partition);
			return true;
		}
		return false;
	}

	private int load(int[] data) // ucitava podatke u RAM po pricipu najbolje odgovarajuce particije
	{
		int size = data.length;
		if (size > Ram.getFreeSpace()) // ako nema mjesta pravimo mjesto
		{
			makeSpace(size);
		}
		int bestPosition = -1, bestFitSize = Integer.MAX_VALUE;
		int currentPosition = -1, currentSize = 0;
		for (int i = 0; i < Ram.getCapacity(); i++) {
			if (Ram.isOcupied(i) && currentSize != 0)// ako poslije praznih naidje na zauzeto
			{
				if (currentSize >= size && currentSize < bestFitSize)// ako je najbolje do sad
				{
					bestFitSize = currentSize;
					bestPosition = currentPosition;
				}
				currentPosition = -1;
				currentSize = 0;
			} else if (!Ram.isOcupied(i) && currentSize == 0) // kad naidje na prvo slobodno poslije zauzetih
			{
				currentPosition = i;
				currentSize = 1;
			} else if (!Ram.isOcupied(i) && currentSize != 0)// broji slobodna mjesta
			{
				currentSize++;
			}

		}
		if (currentSize >= size && currentSize < bestFitSize) // provjerava da li je posljednji blok najbolji
		{
			bestFitSize = currentSize;
			bestPosition = currentPosition;
		}
		if (bestPosition == -1) // ako ne pronadje nijedan blok da odgovara
		{
			defragmentation(); // sazimanje
			bestPosition = Ram.getOccupiedSpace();// kada su podaci sazeti prva slobodna pozicija je na kraju
		}
		if (Ram.setSequence(bestPosition, data))// postavlja podatke na najbolju poziciju
			return bestPosition;
		return -1;
	}

	private void makeSpace(int size) {
		// ukoliko postoje blokirani procesi u ramu
		for (PartitionMemory partition : partitionsInRam) {
			if (partition.getProcess().getState() == ProcessState.BLOCKED) {
				removePartition(partition);
			}
			if (size < Ram.getFreeSpace())
				break;
		}
		// ukoliko ne postoje ili ovim nismo napravili dovoljno mjesta
		// uklanjamo procese koji su zadnje dodati (jer od svih u ramu oni ce se zadnji
		// izvrsavati)
		PartitionMemory lastAdded = partitionsInRam.get(partitionsInRam.size() - 1);
		while (size > Ram.getFreeSpace()) {
			removePartition(lastAdded);
			if (!partitionsInRam.isEmpty())
				lastAdded = partitionsInRam.get(partitionsInRam.size() - 1);
		}
	}

	private void defragmentation() {
		int freePosition = -1;
		boolean avaliablePosition = false;
		for (int i = 0; i < Ram.getCapacity(); i++) {
			if (!Ram.isOcupied(i) && !avaliablePosition)// pronalazi prvu slobodnu poziciju
			{
				freePosition = i;
				avaliablePosition = true;
			} else if (Ram.isOcupied(i) && avaliablePosition)// prva zauzeta pozicija poslije slobodnih
			{
				PartitionMemory partition = PartitionMemory.getPartitionByAddress(i);// pronalazi particiju na poziciji
																						// i
				int size = partition.getSize();
				int j;
				for (j = freePosition; j < freePosition + size; j++) {
					Ram.setAt(j, Ram.getAt(i));// postavlja na j sa zauzetog dijela koji je na i
					Ram.removeAt(i); // uklanja sa i
					i++;
				}
				partition.setPositionInMemory(freePosition);
				i = j - 1; // vraca i na kraj nove pozicije i smanjuje za jedan jer ce u for petlji biti
							// i++
				avaliablePosition = false;
			}
		}
	}

	public static ArrayList<PartitionMemory> getPartitionsInRam() {
		return partitionsInRam;
	}
}
