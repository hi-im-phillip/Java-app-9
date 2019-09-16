package hr.java.vjezbe.sortiranje;

import java.util.Comparator;

import hr.java.vjezbe.entitet.Student;

public class StudentSorter implements Comparator<Student> {

	@Override
	public int compare(Student stu1, Student stu2) {
		int PrezimeCompare = stu1.getPrezime().compareTo(stu2.getPrezime());
		int ImeCompare = stu1.getIme().compareTo(stu2.getPrezime());
		
		if (PrezimeCompare == 0) {
			return ((ImeCompare == 0) ? PrezimeCompare : ImeCompare);
		} else {
			return PrezimeCompare;
		}
		
		
	}

}
