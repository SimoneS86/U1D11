package entità;

public class Student {
	private int id;
	private String name;
	private String lastName;
	private String gender;
	private String birthday;
	private double avg;
	private double minVote;
	private double maxVote;

	public Student(int id, String name, String lastName, String gender, String birthday, double avg, double minVote,
			double maxVote) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.gender = gender;
		this.birthday = birthday;
		this.avg = avg;
		this.minVote = minVote;
		this.maxVote = maxVote;
	}

	public Student(String name, String lastName, String gender, String birthday, double avg, double minVote,
			double maxVote) {
		this.name = name;
		this.lastName = lastName;
		this.gender = gender;
		this.birthday = birthday;
		this.avg = avg;
		this.minVote = minVote;
		this.maxVote = maxVote;
	}

	// Getter e Setter per tutti i campi

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public double getMinVote() {
		return minVote;
	}

	public void setMinVote(double minVote) {
		this.minVote = minVote;
	}

	public double getMaxVote() {
		return maxVote;
	}

	public void setMaxVote(double maxVote) {
		this.maxVote = maxVote;
	}

	@Override
	public String toString() {
		return "Student{" + "id=" + id + ", name='" + name + '\'' + ", lastName='" + lastName + '\'' + ", gender='"
				+ gender + '\'' + ", birthday='" + birthday + '\'' + ", avg=" + avg + ", minVote=" + minVote
				+ ", maxVote=" + maxVote + '}';
	}
}
