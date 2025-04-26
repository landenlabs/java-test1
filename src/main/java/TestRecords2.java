public class TestRecords2 {

    public static record Doctor(String name, String specialty) {}

    public static String cabinet2(Doctor staff) {
        String name = staff.name;
        String specialty = staff.specialty;
        if (specialty.equals("Dermatology"))
            return "The cabinet of " + specialty + " is currently under renovation";
        else if (specialty.equals("Allergy") && name.equals("Kyle Ulm"))
            return "The cabinet of " + specialty + " is closed. The doctor " + name + " is on holiday.";
        else if (specialty.equals("Allergy") && name.equals("John Hora"))
            return  "The cabinet of " + specialty + " is open. The doctor " + name + " is ready to receive patients.";
        return "Cabinet closed";
    }
}
