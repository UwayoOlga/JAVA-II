public class EmailValidator {
    public static void validateEmail(String email) throws IllegalArgumentException {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email format. Email must contain '@' and '.'");
        }
    }

    public static void main(String[] args) {
        try {
            validateEmail("testexample.com");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}