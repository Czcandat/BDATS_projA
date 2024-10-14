package me.bdats_proja;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Obyvatele
{
    // unnecessary?
    public enum Kraj
    {
        Hlavni_mesto_Praha, Jihocesky, Jihomoravsky, Karlovarsky, Kraj_Vysocina, Kralovehradecky, Liberecky, Moravskoslezky, Olomoucky, Pardubicky, Plzensky, Stredocesky, Ustecky, Zlinsky
    }


    public static int importData(String path)
    {
        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(path)))
        {
            String line;

            while ((line = br.readLine()) != null)
            {
                String[] fields = line.split(";");
                if (fields.length != 7) {
                    continue;
                }

                int numKraj = Integer.parseInt(fields[0]);
                int psc = Integer.parseInt(fields[2]);
                String name = fields[3]; // This field is not used in the Record class, but parsed anyway
                int muziPocet = Integer.parseInt(fields[4]);
                int zenyPocet = Integer.parseInt(fields[5]);
                int celkemPocet = Integer.parseInt(fields[6]);

                Obec novaObec = new Obec(psc, name, muziPocet, zenyPocet, celkemPocet);
                Controller.kraje[numKraj].vlozPosledni(novaObec);
                count++;
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
            return -1;
        }
        return count;

    }
}
