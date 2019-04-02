package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	// write your code here
    }
}
class writer {
    public static void main(String[] args) throws IOException {
        //путь к файлу с исходными данными
        String srcFilePath = "C:\\Users\\pc\\Desktop\\txtToRtf\\БИЛЕТЫ_1.txt";

        //работаем с записью в файл
        File readFile = new File(srcFilePath);
        FileReader fr = new FileReader(readFile);
        BufferedReader reader = new BufferedReader(fr);
        WriterTickets wt = new WriterTickets();

        //список вопросов
        List<String> questionList = new ArrayList<String>();

        //шаблонные переменные(специальность/
        //год/кафедра/дисциплина/билет/зав. кафедры)
        //(зав.кафедры вводится отдельно)
        List<String> propList = new ArrayList<String>();

        //ввести название института
        String institute = "Институт вычислительной математики и информационных технологий";
        String line = "";
        int j = 0;

        //цикл пока не закончится файл
        while(line != null){
            System.out.println("-------------" + j + "------------------");

            //проход по файлу до нового блока

            while (!line.equals(institute)) {
                System.out.println(line);
                line = reader.readLine();
            }

            //заполнение листа шаблонных данных
            if(line == null){
                break;
            }
            line = reader.readLine();
            propList.add(institute);
            for (int i = 0; i < 5; i++) {
                propList.add(line);
                line = reader.readLine();
            }

            //вывод шаблонных данных(использовалось для проверки и отладки)
            /*for (int i = 0; i < propList.size(); i++) {
                System.out.println(propList.get(i));
            }
            System.out.println();*/

            //считывание вопросов
            line = reader.readLine();
            while(!line.equals(institute)){

                //игнорирование строк между вопросов
                if(!line.equals("") && !line.equals(".    ") && !line.equals("    ") && !line.equals("   ") && !line.equals(" ")
                        && !line.equals("  ") && line.charAt(0) != ' '){
                    String tmp_line;

                    //отдельное условие для корректного построение матриц
                    if(line.equals("Запрограммировать  построение  массива 3*3 вида")){
                        tmp_line = line;
                        for (int i = 0; i < 3; i++) {
                            tmp_line += "\n";
                            tmp_line += reader.readLine();
                        }
                        System.out.println(tmp_line);
                        questionList.add(tmp_line);
                    }else {
                        questionList.add(line);
                        System.out.println(line);
                    }
                }

                line = reader.readLine();
                if(line == null){
                    break;
                }
            }
            //запись в файл
            wt.changes(propList, questionList);

            //очистка листов для нового блока
            propList.clear();
            questionList.clear();
            j++;
        }
    }
}
class WriterTickets {
    private BufferedWriter bufferedWriter;
    int it = 0;

    //Институт, специальность, год, кафедра, дистциплина, лист вопросов
    public void changes(String speciality, String year, String department, String nameZavKaf, String discipline, List<String> strings) {
        try {

            //путь к файлу с выходными данными
            String filePath = "C:\\Users\\pc\\Desktop\\rtf_test\\Test_" + it + ".rtf";

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, false), "cp1251"));

            //настройки rtf файла
            String s = "{\\rtf1\\ansi\\ansicpg1251\\r\\n{\\fonttbl{\\f0\\froman\\fprq2 Times New Roman;}}\n";

            bufferedWriter.write(s);
            Iterator<String> stringIterator = strings.iterator();
            int numberBullet = 1;
            String help = strings.get(0);

            //цикл пока не закончатся вопросы из блока
            while (stringIterator.hasNext()) {
                String bullet1 = stringIterator.next();
                String bullet2;

                //если вопросов нечетное количество, последним вопросом становится первый вопрос из блока
                if (stringIterator.hasNext()) {
                    bullet2 = stringIterator.next();
                } else {
                    bullet2 = help;
                }

                //формирование одного билета
                s = "{\\f0\\ansicpg1251\\fs024\\qc {\\b Министерство науки и высшего образования Российской Федерации}\\par}\n" +
                        "{\\f0\\ansicpg1251\\qc {\\b ФГАОУ ВО «Казанский (Приволжский) федеральный университет»}\\par}\n" +
                        "{\\f0\\ansicpg1251\\ql Институт вычислительной математики и информационных технологий \\par}\n" +
                        "{\\f0\\ansicpg1251\\ql Специальность: " + speciality + "\\par}\n" +
                        "{\\f0\\ansicpg1251\\ql Год начала подготовки: " + year + "\\par}\n" +
                        "{\\f0\\ansicpg1251\\ql " + department + "\\par}\n" +
                        "{\\f0\\ansicpg1251\\ql Дисциплина: " + discipline + "\\par}\n" +
                        "{\\f0\\ansicpg1251\\qc\\b Зачётный билет №" + numberBullet + "\\par}\n" +
                        "{\\f0\\ansicpg1251\\ql 1. " + bullet1 + "\\par}\n" +
                        "{\\f0\\ansicpg1251\\ql 2. " + bullet2 + "\\par\\par}\n" +
                        "{\\f0\\ansicpg1251\\qr Заведующий кафедрой технологий программирования _______________ /"+nameZavKaf +"/\\par\\par\\par}\n";

                //оставляем по три билета на страницу
                if(numberBullet % 3 == 0){
                    s = s + "{\\page}";
                }
                bufferedWriter.write(s);
                numberBullet++;
            }

            //закрытие rtf файла с помощью закрывающей скобки, без этого файл не читается
            bufferedWriter.write("}");
            bufferedWriter.close();

            //отлов возможных ошибок
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        it++;
    }

    public void changes(List<String> dataUser, List<String> dataBullet) {
        //шаблонные переменные(специальность/год/кафедра/дисциплина/билет/зав. кафедры) (зав.кафедры вводится отдельно)
        String spec = dataUser.get(0);
        String year = dataUser.get(1);
        String kaf = dataUser.get(2);
        String disp = dataUser.get(3);

        //ВВЕСТИ ИМЯ ЗАВЕДУЮЩЕГО КАФЕДРЫ
        String name = "введите имя";

        //логирование
        System.out.println(spec + "\n" + year + "\n" + kaf + "\n" + disp);
        changes(spec, year, kaf, name, disp, dataBullet);
    }
}
