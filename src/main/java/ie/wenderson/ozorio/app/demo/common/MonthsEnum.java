package ie.wenderson.ozorio.app.demo.common;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum MonthsEnum {
	
	January("01"),
    February("02"),
    March("03"),
    April("04"),
    May("05"),
    June("06"),
    July("07"),
    August("08"),
    September("09"),
    October("10"),
    November("11"),
    December("12");
	
	private String name;
	private String value;

	MonthsEnum(String value) {
		name = name();
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    public String getName() {
        return name;
    }
}
