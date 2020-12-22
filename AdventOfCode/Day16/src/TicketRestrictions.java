public class TicketRestrictions {
  private final String fieldType;
  private final int range1Min;
  private final int range1Max;
  private final int range2Min;
  private final int range2Max;

  //creates TicketRestrictions from line in format:
  // "fieldType: range1Min-range1Max or range2Min-range2Max"
  public TicketRestrictions(String line){
    String[] splitLine = line.split(":", 0);

    fieldType = splitLine[0];

    String[] ranges = splitLine[1].split("or");
    String[] range1 = ranges[0].split("-");
    String[] range2 = ranges[1].split("-");
    range1Min = Integer.valueOf(range1[0].trim());
    range1Max = Integer.valueOf(range1[1].trim());
    range2Min = Integer.valueOf(range2[0].trim());
    range2Max = Integer.valueOf(range2[1].trim());
  }

  public boolean isValidValue(int v){
    return (v >= range1Min && v <= range1Max) || (v >=range2Min && v <= range2Max);
  }

  public String getFieldType() {
    return fieldType;
  }
}
