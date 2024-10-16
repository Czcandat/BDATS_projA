package me.bdats_proja;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static me.bdats_proja.Controller.kraje;

public class Obyvatele
{
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
                String[] fields = line.split("[;,]");
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
        if (obec == null) return;

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
        switch (position)
        {
            case FIRST:
                return kraje[kraj.ordinal()].zpristupniPrvni().data;
            case LAST:
                return kraje[kraj.ordinal()].zpristupniPosledni().data;
            case PREV:
                return kraje[kraj.ordinal()].zpristupniPredchudce().data;
            case ACTIVE:
                return kraje[kraj.ordinal()].zpristupniAktualni().data;
            case NEXT:
                return kraje[kraj.ordinal()].zpristupniNaslednika().data;
            case null:
                return null;
            default:
                throw new IllegalArgumentException();
        }
    }


    public static Obec odeberObec(enumPosition position, enumKraj kraj)
    {
        switch (position)
        {
            case FIRST:
                return kraje[kraj.ordinal()].odeberPrvni().data;
            case LAST:
                return kraje[kraj.ordinal()].odeberPosledni().data;
            case PREV:
                return kraje[kraj.ordinal()].odeberPredchudce().data;
            case ACTIVE:
                return kraje[kraj.ordinal()].odeberAktualni().data;
            case NEXT:
                return kraje[kraj.ordinal()].odeberNaslednika().data;
            case null:
                return null;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static float zjistiPrumer(enumKraj kraj)
    {
        float count = 0;
        float average = 0;
        if (kraj.ordinal() == 0)
        {
            for(enumKraj iter: enumKraj.values())
            {
                if (iter.ordinal() == 0) continue;
                average += zjistiPrumer(iter);
                count++;
            }
            return average / count;
        }
        for(Obec obec: kraje[kraj.ordinal()])
        {
            count++;
            average += obec.getCelkemPocet();
        }

        if(count == 0) return 0;

        return average / count;
    }


    public static IAbstrDoubleList<Obec> zobrazObce(enumKraj kraj)
    {
        return kraje[kraj.ordinal()];
    }


    public static IAbstrDoubleList<Obec> zobrazObceNadPrumer(enumKraj kraj)
    {
        float average = zjistiPrumer(kraj);
        IAbstrDoubleList<Obec> result = new IAbstrDoubleList<>();

        for (Obec obec: kraje[kraj.ordinal()])
        {
            if (obec.getCelkemPocet() > average)
            {
                result.vlozNaslednika(obec);
            }
        }
        return result;
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
