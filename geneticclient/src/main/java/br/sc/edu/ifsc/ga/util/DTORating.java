package br.sc.edu.ifsc.ga.util;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.List;

import br.sc.edu.ifsc.ga.domain.Chromosome;

public class DTORating implements Serializable, Remote {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Chromosome chromosome;
	private int id;

	public DTORating() {
	}
	
	
	public static Chromosome[] getAllChromosomes(List<DTORating> dtos) {
		Chromosome[] allChromosomes = new Chromosome[dtos.size()];
		for (int i = 0; i < dtos.size(); i++) {
			allChromosomes[i] = dtos.get(i).getChromosome();
		}

		return allChromosomes;
	}

	public DTORating(Chromosome chromosome, int id) {
		this.chromosome = chromosome;
		this.id = id;
	}

	public Chromosome getChromosome() {
		return chromosome;
	}

	public void setChromosome(Chromosome chromosome) {
		this.chromosome = chromosome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "DTORating{" + "chromosome=" + chromosome + ", id=" + id + '}';
	}
}
