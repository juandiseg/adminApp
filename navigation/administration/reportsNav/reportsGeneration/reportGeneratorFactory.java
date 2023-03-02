package navigation.administration.reportsNav.reportsGeneration;

public class reportGeneratorFactory {
    public iReportable createReportGenerator(String request) {
        iReportable reportGenerator = null;
        if ("SALES".equals(request)) {
            return new salesReportGenerator();
        } else if ("EXPENSES".equals(request)) {
            return new expensesReportGenerator();
        } else if ("GENERAL".equals(request)) {
            return new generalReportGenerator();
        }
        return reportGenerator;
    }
}
