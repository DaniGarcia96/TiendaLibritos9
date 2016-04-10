package factory;

public class ReportFactory {
	public IReport getReport(String typeReport){
		if(typeReport == null){
			return null;
		}
		if(typeReport.equalsIgnoreCase("TEXTREPORT")){
			return new TextReport();
		}
		else if(typeReport.equalsIgnoreCase("XMLREPORT")){
			return new XmlReport();
		}
		else{
			return new NullReport();
		}
	}
}
