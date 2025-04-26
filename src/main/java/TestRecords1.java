public class TestRecords1 {

    public static record Doctor(String name, String specialty) {}

    public static String cabinet1(Doctor staff) {
        return switch (staff) {
            case Doctor(var name, var specialty) when specialty.equals("Dermatology") ->
                    "The cabinet of " + specialty + " is currently under renovation";
            case Doctor(var name, var specialty) when (specialty.equals("Allergy") && name.equals("Kyle Ulm")) ->
                    "The cabinet of " + specialty + " is closed. The doctor " + name + " is on holiday.";
            case Doctor(var name, var specialty) when (specialty.equals("Allergy") && name.equals("John Hora")) ->
                    "The cabinet of " + specialty + " is open. The doctor " + name + " is ready to receive patients.";
            default -> "Cabinet closed";
        };
    }
}
