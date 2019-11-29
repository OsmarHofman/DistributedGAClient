package br.sc.edu.ifsc.ga.algorithm;

import br.sc.edu.ifsc.ga.domain.Chromosome;

public class Boot {

	public static Chromosome[] initialize(int size) {
		Chromosome[] chromosomes = new Chromosome[size];
		for (int i = 0; i < size; i++) {
			chromosomes[i] = new Chromosome().generateRandom();
		}
		return chromosomes;
	}
}
