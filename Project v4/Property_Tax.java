import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;

public class Property_Tax {
    private String fileName = "CSV/Property_Tax_Data.txt";
    private ArrayList<Property_Tax_Data> data = new ArrayList<>();

    public Property_Tax(){
        readCSV();
    }
    
    public double totalTax(String routing){
        return totalAverage(routing,true);
    }
    
    public double averageTax(String routing){
        return totalAverage(routing,false);
    }
    
    public void addPropertyTax(Property p){
        int pick = (int)(Math.random()*2+1);
        if(pick == 1){
            writeToCSV(p.getEircode(),Calendar.getInstance().get(Calendar.YEAR),
                    p.getPropertyTax(),"Paid");
            data.add(new Property_Tax_Data(p.getEircode(),Calendar.getInstance().get(Calendar.YEAR),
                    p.getPropertyTax(),"Paid"));
        }else{
            writeToCSV(p.getEircode(),Calendar.getInstance().get(Calendar.YEAR),
                    p.getPropertyTax(),"Not Paid");
            data.add(new Property_Tax_Data(p.getEircode(),Calendar.getInstance().get(Calendar.YEAR),
                    p.getPropertyTax(),"Not Paid"));
        }
    }
    
    

    private void writeToCSV(String eircode,int year,double propTax,String s){
        try {
            FileWriter fw = new FileWriter(fileName,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(eircode+","+year + "," + propTax + "," + s);
            pw.flush();
            pw.close();

        }catch (Exception E){
            System.out.println("Error writing to csv");
        }
    }

    public ArrayList<Property_Tax_Data> findByProperty(Property p){
        String eircode = p.getEircode();
        ArrayList<Property_Tax_Data> eir = new ArrayList<>();
        for(Property_Tax_Data t:data){
            if(t.equals(eircode)){
                eir.add(t);
            }
        }
        return eir;
    }
    
    private double totalAverage(String routing,boolean total){
        ArrayList<Property_Tax_Data> totalAvg = findByRouting(routing);
        double totalTax = 0;
        int count = 0;
        for(Property_Tax_Data r : totalAvg){
            if(!r.overdue(r.getYear())){
                count++;
                totalTax = totalTax + r.getPropertyTax();
            }
        }
        if(!total && count!=0){
            return totalTax/count;
        }else{
            return totalTax;
        }
    }

    public double taxPercent(String routing){
        ArrayList<Property_Tax_Data> t = findByRouting(routing);
        int amount = t.size();
        int count = 0;
        for(Property_Tax_Data r : t){
            if(r.overdue(r.getYear())){
                count++;
            }
        }
        if(amount>0){
            return 100*((double)count/amount);
        }else{
            return 0;
        }

    }

    private ArrayList<Property_Tax_Data> findByRouting(String routing){
        ArrayList<Property_Tax_Data> route = new ArrayList<>();
        for(Property_Tax_Data r:data){
            if(r.equalRoute(routing)){
                route.add(r);
            }
        }
        return route;
    }


    public ArrayList<Property_Tax_Data> overdue(int year){
        ArrayList<Property_Tax_Data> overdue = new ArrayList<>();
        for(Property_Tax_Data r: data){
            if(r.overdue(year)){
                overdue.add(r);
            }
        }
        return overdue;
    }

    public ArrayList<Property_Tax_Data> overdue(int year,String routing){
        ArrayList<Property_Tax_Data> overdue = overdue(year);
        ArrayList<Property_Tax_Data> overdueRouting = new ArrayList<>();
        for(Property_Tax_Data r : overdue){
            if(r.overdue(routing)){
                overdueRouting.add(r);
            }
        }
        return overdueRouting;
    }
    
    private void readCSV(){
        String line = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null){
                String[] values = line.split(",");
                int year = Integer.parseInt(values[1]);
                double propertyTax = Double.parseDouble(values[2]);
                boolean paid;
                paid = values[3].compareToIgnoreCase("Paid") == 0;
                Property_Tax_Data t = new Property_Tax_Data(values[0],year,propertyTax,paid);
                data.add(t);
            }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
}
//volvunt ad inceptum