package hr.java.vjezbe.baza;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;


public class BazaPodataka {

	private static final String DATABASE_FILE = "database.properties";

	private static Connection connectToDb() throws SQLException, FileNotFoundException, IOException {

		Properties properties = new Properties();
		properties.load(new FileReader(DATABASE_FILE));

		String urlDb = properties.getProperty("dbUrl");
		String username = properties.getProperty("username");
		String pw = properties.getProperty("password");

		Connection conn = DriverManager.getConnection(urlDb, username, pw);

		return conn;

	}

	public static List<Profesor> getProf (Profesor profesor) throws SQLException {

		List<Profesor> listProfesor = new ArrayList<>();

		try {
			Connection conn = connectToDb();
			StringBuilder sqlQuery = new StringBuilder("SELECT * FROM PROFESOR WHERE 1=1");

			if (Optional.ofNullable(profesor).isEmpty() == false) {

				if (Optional.ofNullable(profesor).map(Profesor::getId).isPresent()) {
					sqlQuery.append(" AND ID = " + profesor.getId());
				}
				if (Optional.ofNullable(profesor.getSifra()).map(String::isBlank).orElse(true) == false) {
					sqlQuery.append(" AND SIFRA LIKE '%'" + profesor.getSifra() + '%');
				}
				if (Optional.ofNullable(profesor.getIme()).map(String::isBlank).orElse(true) == false) {
					sqlQuery.append(" AND IME LIKE '%'" + profesor.getIme() + '%');
				}
				if (Optional.ofNullable(profesor.getPrezime()).map(String::isBlank).orElse(true) == false) {
					sqlQuery.append(" AND PREZIME LIKE '%'" + profesor.getPrezime() + '%');
				}
				if (Optional.ofNullable(profesor.getTitula()).map(String::isBlank).orElse(true) == false) {
					sqlQuery.append(" AND TITULA LIKE '%'" + profesor.getTitula() + '%');
				}

			}

			Statement query = conn.createStatement();

			ResultSet resultSet = query.executeQuery(sqlQuery.toString());

			while (resultSet.next()) {

				Long id = resultSet.getLong("id");
				String sifra = resultSet.getString("sifra");
				String ime = resultSet.getString("ime");
				String prezime = resultSet.getString("prezime");
				String titula = resultSet.getString("titula");

				Profesor newProfesor = new Profesor(sifra, ime, prezime, titula, id);
				listProfesor.add(newProfesor);

			}

		} catch (SQLException|IOException e) {
			e.printStackTrace();
		}

		return listProfesor;
	}

	public List<Profesor> getAllProfs() {

		List<Profesor> listProf = new ArrayList<>();

		try (Connection conn = connectToDb()) {

			Statement statement;
			statement = conn.createStatement();
			String sqlQuery = "SELECT * FROM PROFESOR";
			ResultSet resultSet;
			resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				Profesor profesor = new Profesor(resultSet.getString("sifra"), resultSet.getString("ime"), resultSet.getString("prezime"), resultSet.getString("titula"), resultSet.getLong("id"));
				listProf.add(profesor);

			}
		} catch (SQLException|IOException e) {

			e.printStackTrace();

		}
		return listProf;

	}

	public List<Student> getAllStud() {

		List<Student> listStud = new ArrayList<>();

		try (Connection conn = connectToDb()) {

			Statement statement = conn.createStatement();
			String sqlQuery = "SELECT * FROM STUDENT";
			ResultSet resultSet = statement.executeQuery(sqlQuery);

			while (resultSet.next()) {
				Student student = new Student(resultSet.getString("ime"),
						                      resultSet.getString("prezime"),
						                      resultSet.getString("jmbag"),
						                      resultSet.getTimestamp("datum_rodjenja").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
						                      resultSet.getLong("id"));
				listStud.add(student);
			}
		} catch (SQLException|IOException e) {
			e.printStackTrace();
		}

		return listStud;
	}

	public List<Predmet> getAllPred() {

		List<Profesor> listProfesor = getAllProfs();
		List<Predmet> listPred = new ArrayList<>();

		try (Connection con = connectToDb()) {
			Statement statement = con.createStatement();
			String sqlQuery = "SELECT * FROM PREDMET";
			ResultSet resultSet = statement.executeQuery(sqlQuery);

			while (resultSet.next()) {
				Predmet predmet = new Predmet(resultSet.getString("sifra"),
						                      resultSet.getString("naziv"),
						                      resultSet.getInt("broj_ects_bodova"),
						                      listProfesor.get(resultSet.getInt("profesor_id")),
						                      resultSet.getLong("id"));
			listPred.add(predmet);
			}
		} catch (SQLException|IOException e) {
			e.printStackTrace();
		}

		return listPred;
	}

	public List<Ispit> getAllIspit() {

		List<Predmet> listPred = getAllPred();
		List<Student> listStud = getAllStud();
		List<Ispit> listIspita = new ArrayList<>();

		try (Connection connection = connectToDb()) {
			Statement statement = connection.createStatement();
			String sqlQuery = "SELECT * FROM ISPIT";
			ResultSet resultSet = statement.executeQuery(sqlQuery);

			while (resultSet.next()) {
				Ispit ispit = new Ispit(listPred.get(resultSet.getInt("predmet_id")), listStud.get(resultSet.getInt("student_id")), resultSet.getInt("ocjena"), resultSet.getTimestamp("datum_i_vrijeme").toLocalDateTime(), resultSet.getLong("id"));
				listIspita.add(ispit);
			}
		} catch (SQLException|IOException e) {
			e.printStackTrace();
		}
		return listIspita;
	}

	public void spremiNovogProfesora(Profesor profesor) {
		try (Connection conn = connectToDb()) {
			PreparedStatement preparedStatement = conn.prepareStatement( "INSERT INTO PROFESOR(ime, prezime, sifra, titula) VALUES (?, ?, ?, ?)");
			preparedStatement.setString(1, profesor.getIme());
			preparedStatement.setString(2, profesor.getPrezime());
			preparedStatement.setString(3, profesor.getSifra());
			preparedStatement.setString(4, profesor.getTitula());
			preparedStatement.executeUpdate();
			} catch (SQLException | IOException e) {
				e.printStackTrace();

				}
		}

	public void insertNewStud(Student student) {

		try (Connection conn = connectToDb()) {
			PreparedStatement preparedStatement = conn.prepareStatement( "INSERT INTO STUDENT(ime, prezime, jmbag, datum_rodjenja) VALUES (?, ?, ?, ?)");
			preparedStatement.setString(1, student.getIme());
			preparedStatement.setString(2, student.getPrezime());
			preparedStatement.setString(3, student.getJmbag());
			preparedStatement.setDate(4, Date.valueOf(student.getDatumRodenja()));
			preparedStatement.executeUpdate();
			} catch (SQLException | IOException e) {
				e.printStackTrace();

				}
		}


	public void insertNewPred(Predmet predmet) {

		try (Connection conn = connectToDb()) {
			PreparedStatement preparedStatement = conn.prepareStatement( "INSERT INTO PROFESOR(ime, prezime, sifra, titula) VALUES (?, ?, ?, ?)");
			preparedStatement.setString(1, predmet.getSifra());
			preparedStatement.setString(2, predmet.getNaziv());
			preparedStatement.setInt(3, predmet.getBrojEctsBodova());
			preparedStatement.setInt(4, ((int) predmet.getNositelj().getId()));
			preparedStatement.executeUpdate();
			} catch (SQLException | IOException e) {
				e.printStackTrace();

				}
		}

	public void insertNewIspit(Ispit ispit) {

		try (Connection conn = connectToDb()) {
			PreparedStatement preparedStatement = conn.prepareStatement( "INSERT INTO PROFESOR(ime, prezime, sifra, titula) VALUES (?, ?, ?, ?)");
			preparedStatement.setInt(1, (int) ispit.getPredmet().getId());
			preparedStatement.setInt(2, (int) ispit.getStudent().getId());
			preparedStatement.setInt(3, ispit.getOcjena());
			preparedStatement.setTimestamp(4, Timestamp.valueOf(ispit.getDatumIVrijeme()));
			preparedStatement.executeUpdate();
			} catch (SQLException | IOException e) {
				e.printStackTrace();

				}

	}

	}




