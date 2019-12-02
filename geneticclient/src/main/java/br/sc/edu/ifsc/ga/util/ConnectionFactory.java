package br.sc.edu.ifsc.ga.util;

import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;

import br.sc.edu.ifsc.ga.interfaces.IRating;

public class ConnectionFactory {
	private List<DTORating> result;

	private int fila;
	private List<DTORating> respA;
	private List<DTORating> respB;
	private List<DTORating> respC;
	private List<DTORating> respD;

	public ConnectionFactory() {
		fila = 0;
		respA = new ArrayList<DTORating>();
		respB = new ArrayList<DTORating>();
		respC = new ArrayList<DTORating>();
		respD = new ArrayList<DTORating>();
	}

	public void conectar(String caminho, DTOServerData serverData, int serverId) {
		fila++;

		new Thread() {
			@Override
			public void run() {
				try {
					IRating rate = (IRating) Naming.lookup(caminho);
					if (serverId == 1) {
						DTOServerData dataPart1 = splitChromosomes(serverData, 0, 25);
						respA = new ArrayList<DTORating>();
						respA.addAll(rate.rate(dataPart1));
					} else if (serverId == 2) {
						DTOServerData dataPart2 = splitChromosomes(serverData, 25, 50);
						respB = new ArrayList<DTORating>();

						respB.addAll(rate.rate(dataPart2));

					} else if (serverId == 3) {
						DTOServerData dataPart3 = splitChromosomes(serverData, 50, 75);
						respC = new ArrayList<DTORating>();

						respC.addAll(rate.rate(dataPart3));
					} else {
						DTOServerData dataPart4 = splitChromosomes(serverData, 75, 100);
						respD = new ArrayList<DTORating>();

						respD.addAll(rate.rate(dataPart4));
					}
					fila--;
					System.out.println("Tamanho da Fila - Remove: " + fila);
				} catch (Exception e) {
					System.err.println("\tErro ao conectar com o Servidor: " + e.getMessage());
					System.exit(1);
				}
			}
		}.start();

	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public List<DTORating> getRespA() {
		return respA;
	}

	public void setRespA(List<DTORating> respA) {
		this.respA = respA;
	}

	public List<DTORating> getRespB() {
		return respB;
	}

	public void setRespB(List<DTORating> respB) {
		this.respB = respB;
	}

	public List<DTORating> getRespC() {
		return respC;
	}

	public void setRespC(List<DTORating> respC) {
		this.respC = respC;
	}

	public List<DTORating> getRespD() {
		return respD;
	}

	public void setRespD(List<DTORating> respD) {
		this.respD = respD;
	}

	private DTOServerData splitChromosomes(DTOServerData serverData, int comeco, int fim) {
		DTOServerData newServerData = new DTOServerData();
		newServerData.setClasses(serverData.getClasses());
		newServerData.setLessons(serverData.getLessons());
		newServerData.setSubjects(serverData.getSubjects());
		newServerData.setTeachers(serverData.getTeachers());
		for (int i = comeco; i < fim; i++) {
			newServerData.addChromosome(serverData.getChromosomes().get(i));
		}
		return newServerData;

	}
}
