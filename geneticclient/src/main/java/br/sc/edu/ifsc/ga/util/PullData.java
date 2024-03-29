package br.sc.edu.ifsc.ga.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.sc.edu.ifsc.ga.domain.Chromosome;
import br.sc.edu.ifsc.ga.domain.Classes;
import br.sc.edu.ifsc.ga.domain.Lesson;
import br.sc.edu.ifsc.ga.domain.Subject;
import br.sc.edu.ifsc.ga.domain.Teacher;

public class PullData {

	private static DTOServerData serverData;

	public static DTOServerData getAllData(Chromosome[] chromosomes) {
		setUp();
		serverData.setChromosomes(Arrays.asList(chromosomes));
		try {
			File fXmlFile = new File("src/resources/dados.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList classe = doc.getElementsByTagName("class");
			getData(classe, 0);

			NodeList lesson = doc.getElementsByTagName("lesson");
			getData(lesson, 1);

			NodeList subject = doc.getElementsByTagName("subject");
			getData(subject, 2);

			NodeList teacher = doc.getElementsByTagName("teacher");
			getData(teacher, 3);

		} catch (Exception e) {
			System.err.println("Erro ao tentar puxar dados do xml: " + e.getMessage());
			System.exit(1);
		}

		return serverData;

	}

	private static Object getData(NodeList nList, int column) {
		try {
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					switch (column) {
					case 0:
						// Classes
						int idClass = Integer.parseInt(eElement.getAttribute("id"));
						String nameClass = eElement.getAttribute("name");
						String shortNameClass = eElement.getAttribute("short");
						int teacherIdClass = Integer.parseInt(eElement.getAttribute("teacherid"));
						String timeoffClass = eElement.getAttribute("timeoff");
						serverData.getClasses()
								.add(new Classes(idClass, nameClass, shortNameClass, teacherIdClass, timeoffClass));
						break;
					case 1:
						// Lesson
						int idLesson = Integer.parseInt(eElement.getAttribute("id"));
						int subjectId = Integer.parseInt(eElement.getAttribute("subjectid"));
						int classesId = Integer.parseInt(eElement.getAttribute("classid"));
						int teacherIdLesson;
						if (eElement.getAttribute("teacherid").equals("")) {
							teacherIdLesson = -1;
						} else {
							teacherIdLesson = Integer.parseInt(eElement.getAttribute("teacherid"));
						}

						int periodsPerWeek = Integer.parseInt(eElement.getAttribute("periodsperweek"));
						serverData.getLessons()
								.add(new Lesson(idLesson, subjectId, classesId, teacherIdLesson, periodsPerWeek));
						break;
					case 2:
						// Subject
						int idSubject = Integer.parseInt(eElement.getAttribute("id"));
						String nameSubject = eElement.getAttribute("name");
						String shortNameSubject = eElement.getAttribute("short");
						serverData.getSubjects().add(new Subject(idSubject, nameSubject, shortNameSubject));
						break;
					case 3:
						// Teacher
						int idTeacher = Integer.parseInt(eElement.getAttribute("id"));
						String nameTeacher = eElement.getAttribute("name");
						String shortNameTeacher = eElement.getAttribute("short");
						String timeoffTeacher = eElement.getAttribute("timeoff");
						serverData.getTeachers()
								.add(new Teacher(idTeacher, nameTeacher, shortNameTeacher, timeoffTeacher));
						break;
					default:
						System.out.println("Não existente");
						break;
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Erro ao tentar pegar dados específicos: " + e.getMessage());
			System.exit(1);
		}
		return null;
	}

	private static void setUp() {
		serverData = new DTOServerData();
		serverData.setClasses(new ArrayList<Classes>());
		serverData.setLessons(new ArrayList<Lesson>());
		serverData.setSubjects(new ArrayList<Subject>());
		serverData.setTeachers(new ArrayList<Teacher>());
	}
}
