package br.sc.edu.ifsc.ga.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import br.sc.edu.ifsc.ga.domain.Chromosome;
import br.sc.edu.ifsc.ga.util.RatingHandler;

public class Crossover {

	private static Random random = new Random();

	private static Chromosome[] tinder(Chromosome parent1, Chromosome parent2) {
		int parentSize = parent1.getClassrooms().length;
		Chromosome child1 = new Chromosome(0);
		Chromosome child2 = new Chromosome(0);

		int crossPoint = random.nextInt(parentSize + 1);
		if (parent1.equals(parent2) || crossPoint == 0 || crossPoint == parentSize) {
			child1.setParentToChild(child1, parent1);
			child2.setParentToChild(child2, parent2);
		} else {
			for (int i = 0; i < crossPoint; i++) {
				child1.setGene(parent1.getGene(i), i);
				child2.setGene(parent2.getGene(i), i);
			}
			for (int j = crossPoint; j < parentSize; j++) {
				child1.setGene(parent2.getGene(j), j);
				child2.setGene(parent1.getGene(j), j);
			}
		}
		return new Chromosome[] { child1, child2 };
	}

	public static Chromosome[] cross(RatingHandler[] ratingHandler, int faA, Chromosome[] chromosomes,
			Chromosome[] eliteChromosomes, int percentageChanceOfCrossover) {
		int size = (chromosomes.length - eliteChromosomes.length) / 2;
		List<Chromosome> lChromosomes = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			int parentRNG = random.nextInt(faA) + 1;
			Chromosome p1 = generateParent(parentRNG, ratingHandler, chromosomes);
			parentRNG = random.nextInt(faA) + 1;
			Chromosome p2 = generateParent(parentRNG, ratingHandler, chromosomes);
			assert p1 != null;
			assert p2 != null;
			if ((random.nextInt(100) < percentageChanceOfCrossover)) {
				Chromosome[] children = tinder(p1, p2);
				lChromosomes.addAll(Arrays.asList(children));
			} else {
				lChromosomes.addAll(Arrays.asList(p1, p2));
			}
		}
		lChromosomes.addAll(Arrays.asList(eliteChromosomes));
		return lChromosomes.toArray(chromosomes);
	}

	private static Chromosome generateParent(int parentRNG, RatingHandler[] ratingHandler, Chromosome[] chromosomes) {
		for (int i = 0; i < chromosomes.length; i++) {
			if (i == 0) {
				if (parentRNG <= ratingHandler[0].getFaA()) {
					return chromosomes[ratingHandler[0].getChromosomeId()];
				}
			} else {
				if (parentRNG > ratingHandler[i - 1].getFaA() && parentRNG <= ratingHandler[i].getFaA()) {
					return chromosomes[ratingHandler[i - 1].getChromosomeId()];
				}
			}
		}
		return null;
	}

}