package br.sc.edu.ifsc.ga.algorithm;

import java.util.Random;

import br.sc.edu.ifsc.ga.domain.Chromosome;
import br.sc.edu.ifsc.ga.domain.Classroom;

public class Mutation {

	public static Chromosome[] randomMutation(Chromosome[] chromosomes, int proportion) {
		Random random = new Random();
		if ((random.nextInt(100) < proportion)) {
			int chromosomeRandomPosition = random.nextInt(chromosomes.length);
			Classroom[] classrooms = chromosomes[chromosomeRandomPosition].getClassrooms();
			int classroomRandomPosition = random.nextInt(classrooms.length);
			int clarroomSubjectRandomPosition = random
					.nextInt(classrooms[classroomRandomPosition].getClassroomSubjects().length);
			if (classroomRandomPosition < 30) {
				classrooms[classroomRandomPosition].setClassroomSubjectInIndex(clarroomSubjectRandomPosition,
						random.nextInt(30));
			} else if (classroomRandomPosition < 39) {
				classrooms[classroomRandomPosition].setClassroomSubjectInIndex(clarroomSubjectRandomPosition,
						random.nextInt(9) + 30);
			}
		}
		return chromosomes;
	}
}
