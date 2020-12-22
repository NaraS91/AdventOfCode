import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.print.DocFlavor.INPUT_STREAM;

public class Solver {
  public static TaskArgs parseInput(String filePath){
    Scanner scanner;

    try {
      scanner = new Scanner(new File(filePath));
    } catch(FileNotFoundException e) {
      throw new IllegalArgumentException("file not found");
    }

    List<TicketRestrictions> ticketRestrictions = new LinkedList<>();

    String nextLine = scanner.nextLine();
    while(!nextLine.isEmpty()){
      ticketRestrictions.add(new TicketRestrictions(nextLine));
      nextLine = scanner.nextLine();
    }

    scanner.nextLine();
    nextLine = scanner.nextLine();

    Ticket mainTicket = new Ticket(nextLine);

    scanner.nextLine();
    scanner.nextLine();

    List<Ticket> otherTickets = new LinkedList<>();

    while(scanner.hasNextLine()){
      otherTickets.add(new Ticket(scanner.nextLine()));
    }
    return new TaskArgs(ticketRestrictions, mainTicket, otherTickets);
  }

  private static class TaskArgs{
    List<TicketRestrictions> ticketRestrictions;
    Ticket mainTicket;
    List<Ticket> otherTickets;

    public TaskArgs(List<TicketRestrictions> ticketRestrictions, Ticket mainTicket, List<Ticket> otherTickets){
      this.ticketRestrictions = ticketRestrictions;
      this.mainTicket = mainTicket;
      this.otherTickets = otherTickets;
    }
  }

  private static class Ticket{
    List<Integer> values;

    public Ticket(String line){
      String[] values = line.split(",");
      this.values = new LinkedList<>();

      for(String value : values)
        this.values.add(Integer.valueOf(value));
    }
  }

  private static boolean isValidTicketValue(int v, List<TicketRestrictions> ticketRestrictions){
    return ticketRestrictions.stream().anyMatch(r -> r.isValidValue(v));
  }

  private static boolean isValidTicket(Ticket ticket, List<TicketRestrictions> ticketRestrictions){
    return ticket.values.stream().allMatch(v -> isValidTicketValue(v, ticketRestrictions));
  }

  public static int solve1(TaskArgs args){
    int result = 0;

    for(Ticket ticket : args.otherTickets){
      result += ticket.values.stream()
                  .filter(v -> !isValidTicketValue(v, args.ticketRestrictions))
                  .reduce(0, Integer::sum);
    }

    return result;
  }

  private static Set<Integer> ticketValuePositions(int value, List<TicketRestrictions> ticketRestrictions){
    Set<Integer> result = new HashSet<>();

    for(int i = 0; i < ticketRestrictions.size(); i++){
      if(ticketRestrictions.get(i).isValidValue(value))
        result.add(i);
    }

    return result;
  }

  public static long solve2(TaskArgs args){
    List<Ticket> validTickets
        = args.otherTickets.stream()
          .filter(ticket -> isValidTicket(ticket, args.ticketRestrictions))
          .collect(Collectors.toList());

    int numberOfPositions = args.mainTicket.values.size();
    List<Set<Integer>> possiblePositions = new ArrayList<>();
    List<Integer> ints
        = IntStream.range(0, numberOfPositions)
          .boxed()
          .collect(Collectors.toList());

    for(int i = 0; i < numberOfPositions; i++){
      possiblePositions.add(new HashSet<>(ints));
    }

    for (Ticket ticket : validTickets){
      for(int i = 0; i < ticket.values.size(); i++){
        possiblePositions.get(i).
            retainAll(ticketValuePositions(ticket.values.get(i), args.ticketRestrictions));
      }
    }

    Set<Integer> notUsed = new HashSet<>(ints);
    int[] resultAssignements = new int[numberOfPositions];
    while(!notUsed.isEmpty()){
      int found = -1;
      for(int i = 0; i < possiblePositions.size(); i++){
        if(possiblePositions.get(i).size() == 1){
          found = i;
          break;
        }
      }

      if (found > -1){
        int pos = possiblePositions.get(found).iterator().next();
        notUsed.remove(pos);
        resultAssignements[found] = pos;

        //remove position from other sets
        for(Set<Integer> posPossitions : possiblePositions)
          posPossitions.remove(pos);
      } else {
        System.err.println("if its here, its gonna be a pain :(");
      }
    }

    long result = 1;
    for(int i = 0; i < numberOfPositions; i++){
      if(args.ticketRestrictions.get(resultAssignements[i]).getFieldType().startsWith("departure"))
        result *= args.mainTicket.values.get(i);
    }

    return result;
  }

  public static void main(String[] args) {
    TaskArgs taskArgs = parseInput("src/input");
    System.out.println(solve1(taskArgs));
    System.out.println(solve2(taskArgs));
  }
}
