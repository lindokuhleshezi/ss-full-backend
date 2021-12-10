package za.ac.ss.annotation;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RSAIDNumberValidator implements ConstraintValidator<RSAIDNumber, String> {

	@Override
	public boolean isValid(String idnumber, ConstraintValidatorContext context) {
		return idnumber != "" ||  idnumber != "" ? RSAIDNumberValidator.validateRSAIDNo(idnumber) : true;
	}

	private static boolean validateDate(String date) {
		int year = Integer.parseInt(date.substring(0, 2));
		int month = Integer.parseInt(date.substring(2, 4));

		if (month < 1 || month > 12) {
			return false;
		}

		int day = Integer.parseInt(date.substring(4, 6));
		Calendar myCal = new GregorianCalendar(year, month, day);
		int maxDays = myCal.getActualMaximum(Calendar.DAY_OF_MONTH);
		if (day < 1 || day > maxDays) {
			return false;
		}

		return true;
	}

	public static boolean validateRSAIDNo(String idNumber) {
		try {
			Pattern pattern = Pattern.compile("[0-9]{13}");
			Matcher matcher = pattern.matcher(idNumber);

			if (!matcher.matches()) {
				return false;
			}

			if (!validateDate(idNumber.substring(0, 6))) {
				return false;
			}
			int lastNumber = Integer.parseInt(String.valueOf(idNumber.charAt(idNumber.length() - 1)));
			String numberSection = idNumber.substring(0, idNumber.length() - 1);

			return lastNumber == generateLuhnDigit(numberSection);
		} catch (Exception ex) {
			return false;
		}
	}

	private static int generateLuhnDigit(String input) {
		int total = 0;
		int count = 0;
		for (int i = 0; i < input.length(); i++) {
			int multiple = (count % 2) + 1;
			count++;
			int temp = multiple * Integer.parseInt(String.valueOf(input.charAt(i)));
			temp = (int) Math.floor(temp / 10) + (temp % 10);
			total += temp;
		}

		total = (total * 9) % 10;

		return total;
	}

//	private static Date getBirthdate(final String idNumber) {
//		int year = Integer.parseInt(idNumber.substring(0, 2));
//		int currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100;
//
//		int prefix = 1900;
//		if (year < currentYear) {
//			prefix = 2000;
//		}
//		year += prefix;
//
//		int month = Integer.parseInt(idNumber.substring(2, 4));
//		int day = Integer.parseInt(idNumber.substring(4, 6));
//
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTimeInMillis(0);
//		calendar.set(Calendar.YEAR, year);
//		calendar.set(Calendar.MONTH, month - 1);
//		calendar.set(Calendar.DAY_OF_MONTH, day);
//
//		return calendar.getTime();
//	}
}
