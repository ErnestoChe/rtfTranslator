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
        //���� � ����� � ��������� �������
        String srcFilePath = "C:\\Users\\pc\\Desktop\\txtToRtf\\������_1.txt";

        //�������� � ������� � ����
        File readFile = new File(srcFilePath);
        FileReader fr = new FileReader(readFile);
        BufferedReader reader = new BufferedReader(fr);
        WriterTickets wt = new WriterTickets();

        //������ ��������
        List<String> questionList = new ArrayList<String>();

        //��������� ����������(�������������/
        //���/�������/����������/�����/���. �������)
        //(���.������� �������� ��������)
        List<String> propList = new ArrayList<String>();

        //������ �������� ���������
        String institute = "�������� �������������� ���������� � �������������� ����������";
        String line = "";
        int j = 0;

        //���� ���� �� ���������� ����
        while(line != null){
            System.out.println("-------------" + j + "------------------");

            //������ �� ����� �� ������ �����

            while (!line.equals(institute)) {
                System.out.println(line);
                line = reader.readLine();
            }

            //���������� ����� ��������� ������
            if(line == null){
                break;
            }
            line = reader.readLine();
            propList.add(institute);
            for (int i = 0; i < 5; i++) {
                propList.add(line);
                line = reader.readLine();
            }

            //����� ��������� ������(�������������� ��� �������� � �������)
            /*for (int i = 0; i < propList.size(); i++) {
                System.out.println(propList.get(i));
            }
            System.out.println();*/

            //���������� ��������
            line = reader.readLine();
            while(!line.equals(institute)){

                //������������� ����� ����� ��������
                if(!line.equals("") && !line.equals(".    ") && !line.equals("    ") && !line.equals("   ") && !line.equals(" ")
                        && !line.equals("  ") && line.charAt(0) != ' '){
                    String tmp_line;

                    //��������� ������� ��� ����������� ���������� ������
                    if(line.equals("�����������������  ����������  ������� 3*3 ����")){
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
            //������ � ����
            wt.changes(propList, questionList);

            //������� ������ ��� ������ �����
            propList.clear();
            questionList.clear();
            j++;
        }
    }
}
class WriterTickets {
    private BufferedWriter bufferedWriter;
    int it = 0;

    //��������, �������������, ���, �������, �����������, ���� ��������
    public void changes(String speciality, String year, String department, String nameZavKaf, String discipline, List<String> strings) {
        try {

            //���� � ����� � ��������� �������
            String filePath = "C:\\Users\\pc\\Desktop\\rtf_test\\Test_" + it + ".rtf";

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, false), "cp1251"));

            //��������� rtf �����
            String s = "{\\rtf1\\ansi\\ansicpg1251\\r\\n{\\fonttbl{\\f0\\froman\\fprq2 Times New Roman;}}\n";

            bufferedWriter.write(s);
            Iterator<String> stringIterator = strings.iterator();
            int numberBullet = 1;
            String help = strings.get(0);

            //���� ���� �� ���������� ������� �� �����
            while (stringIterator.hasNext()) {
                String bullet1 = stringIterator.next();
                String bullet2;

                //���� �������� �������� ����������, ��������� �������� ���������� ������ ������ �� �����
                if (stringIterator.hasNext()) {
                    bullet2 = stringIterator.next();
                } else {
                    bullet2 = help;
                }

                //������������ ������ ������
                s = "{\\f0\\ansicpg1251\\fs024\\qc {\\b ������������ ����� � ������� ����������� ���������� ���������}\\par}\n" +
                        "{\\f0\\ansicpg1251\\qc {\\b ����� �� ���������� (�����������) ����������� �����������}\\par}\n" +
                        "{\\f0\\ansicpg1251\\ql �������� �������������� ���������� � �������������� ���������� \\par}\n" +
                        "{\\f0\\ansicpg1251\\ql �������������: " + speciality + "\\par}\n" +
                        "{\\f0\\ansicpg1251\\ql ��� ������ ����������: " + year + "\\par}\n" +
                        "{\\f0\\ansicpg1251\\ql " + department + "\\par}\n" +
                        "{\\f0\\ansicpg1251\\ql ����������: " + discipline + "\\par}\n" +
                        "{\\f0\\ansicpg1251\\qc\\b �������� ����� �" + numberBullet + "\\par}\n" +
                        "{\\f0\\ansicpg1251\\ql 1. " + bullet1 + "\\par}\n" +
                        "{\\f0\\ansicpg1251\\ql 2. " + bullet2 + "\\par\\par}\n" +
                        "{\\f0\\ansicpg1251\\qr ���������� �������� ���������� ���������������� _______________ /"+nameZavKaf +"/\\par\\par\\par}\n";

                //��������� �� ��� ������ �� ��������
                if(numberBullet % 3 == 0){
                    s = s + "{\\page}";
                }
                bufferedWriter.write(s);
                numberBullet++;
            }

            //�������� rtf ����� � ������� ����������� ������, ��� ����� ���� �� ��������
            bufferedWriter.write("}");
            bufferedWriter.close();

            //����� ��������� ������
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
        //��������� ����������(�������������/���/�������/����������/�����/���. �������) (���.������� �������� ��������)
        String spec = dataUser.get(0);
        String year = dataUser.get(1);
        String kaf = dataUser.get(2);
        String disp = dataUser.get(3);

        //������ ��� ����������� �������
        String name = "������� ���";

        //�����������
        System.out.println(spec + "\n" + year + "\n" + kaf + "\n" + disp);
        changes(spec, year, kaf, name, disp, dataBullet);
    }
}
