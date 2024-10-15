package me.bdats_proja;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static me.bdats_proja.Controller.kraje;

public class Obyvatele
{
    // unnecessary?
    public enum enumKraj
    {
        ALL, PRAHA, JIHOCESKY, JIHOMORAVSKY, KARLOVARSKY, VYSOCINA, KRALOVEHRADECKY, LIBERECKY, MORAVSKOSLEZKY, OLOMOUCKY, PARDUBICKY, PLZENSKY, STREDOCESKY, USTECKY, ZLINSKY
    }

    public enum enumPosition
    {
        FIRST, LAST, PREV, NEXT, ACTIVE
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
                kraje[numKraj].vlozPosledni(novaObec);
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

    public static void vlozObec(Obec obec, enumPosition position, enumKraj kraj)
    {
        switch(position)
        {
            case FIRST:
                kraje[kraj.ordinal()].vlozPrvni(obec);
                break;
            case LAST:
                kraje[kraj.ordinal()].vlozPosledni(obec);
                break;
            case PREV:
                kraje[kraj.ordinal()].vlozPredchudce(obec);
                break;
            case NEXT:
                kraje[kraj.ordinal()].vlozNaslednika(obec);
                break;
            case null, default:
        }

    }

    public static Obec zpristupniObec(enumPosition position, enumKraj kraj)
    {
        return switch (position) {
            case FIRST -> kraje[kraj.ordinal()].zpristupniPrvni().data;
            case LAST -> kraje[kraj.ordinal()].zpristupniPosledni().data;
            case PREV -> kraje[kraj.ordinal()].zpristupniPredchudce().data;
            case ACTIVE -> kraje[kraj.ordinal()].zpristupniAktualni().data;
            case NEXT -> kraje[kraj.ordinal()].zpristupniNaslednika().data;
            case null, default -> null;
        };
    }

    public static Obec odeberObec(enumPosition position, enumKraj kraj)
    {
        return switch (position) {
            case FIRST -> kraje[kraj.ordinal()].odeberPrvni().data;
            case LAST -> kraje[kraj.ordinal()].odeberPosledni().data;
            case PREV -> kraje[kraj.ordinal()].odeberPredchudce().data;
            case ACTIVE -> kraje[kraj.ordinal()].odeberAktualni().data;
            case NEXT -> kraje[kraj.ordinal()].odeberNaslednika().data;
            case null, default -> null;
        };
    }

    public static float zjistiPrumer(enumKraj kraj)
    {
        int count = 0;
        float avrage = 0;
        if (kraj.ordinal() == 0)
        {
            for(enumKraj iter: enumKraj.values())
            {
                if (iter.ordinal() == 0) continue;
                avrage += zjistiPrumer(iter);
                count++;
            }
            return avrage / count;
        }
        for(Obec obec: kraje[kraj.ordinal()])
        {
            count++;
            avrage += obec.getCelkemPocet();
        }
        return avrage / count;
    }

    public static void zobrazObce(enumKraj kraj)
    {
        if (kraj.ordinal() == 0)
        {
            for(enumKraj iter: enumKraj.values())
            {
                if (iter.ordinal() == 0) continue;
                zobrazObce(iter);
            }
            return;
        }
        for(Obec obec: kraje[kraj.ordinal()])
        {
            // TODO some operation of visualization
            System.out.println(obec.getName());
        }
    }

    public static void zobrazObceNadPrumer(enumKraj kraj)
    {
        float avrage = zjistiPrumer(kraj);
        // TODO kraj == 0 behaviour
        /*
        if (kraj.ordinal() == 0)
        {
            for (enumKraj iter: enumKraj.values())
            {
                if (iter.ordinal() == 0) continue;
                zobrazObceNadPrumer(iter);
            }
        }
        */
        for (Obec obec: kraje[kraj.ordinal()])
        {
            if (obec.getCelkemPocet() >= avrage)
            {
                // TODO some operation of visualization
                System.out.println(obec.getName());
            }
        }
    }

    public static void zrus(enumKraj kraj)
    {
        if (kraj.ordinal() == 0)
        {
            for (enumKraj iter: enumKraj.values())
            {
                if (iter.ordinal() == 0) continue;
                zrus(iter);
            }
        }
        kraje[kraj.ordinal()].zrus();
    }
}
