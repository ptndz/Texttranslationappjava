import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class App {
    private JPanel panel1;
    private JComboBox fromComboBox;
    private JComboBox comboBox2;
    private JButton translateButton;
    private JEditorPane editorPane1;
    private JEditorPane editorPane2;
    private JButton historyButton;
    private JTable table1;
    private JPanel historyjpanel;
    public JComboBox listKeyLanguage;
    public String[] langKey;

    public static String[] getList(String filePath) throws IOException {
        String[] list = {};
        String url = filePath;
        // Đọc dữ liệu từ File với Scanner
        FileInputStream fileInputStream = new FileInputStream(url);
        Scanner scanner = new Scanner(fileInputStream);
        String ListKeys;
        try {
            while (scanner.hasNextLine()) {
                ListKeys = scanner.nextLine();
                list = ListKeys.split(";");
            }
        } finally {
            try {
                scanner.close();
                fileInputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return list;
    }

    //    public  static  String capitalize(String name)
//    {
//        String s_uper;
//        String firstLetter = name.substring(0, 1);
//        String remainingLetters = name.substring(1, name.length());
//        firstLetter = firstLetter.toUpperCase();
//        s_uper = firstLetter + remainingLetters;
//        return  s_uper;
//    }
    private static String translate(String langFrom, String langTo, String text) throws IOException {
        // INSERT YOU URL HERE
        String urlStr = "https://script.google.com/macros/s/AKfycbwFwSXxmjEaTwJPK9BnR5QXWf9RS8MqVnY23Ay0eqK7z_sHby-6/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    public App() {
        //table history
        
        Object[] columns = {"", "From", "To"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);

        // set the model to the table

        table1.setModel(model);

        // quy dinh bang co bao nhieu cot
        Object[] row = new Object[3];

        row[0] = "1";
        row[1] = "2";
        row[2] = "3";

        // add row to the model
        model.addRow(row);

        //table history hide
        historyjpanel.setVisible(false);

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (historyjpanel.isVisible()) {
                    //table history hide
                    historyjpanel.setVisible(false);
                } else {
                    //table history show
                    historyjpanel.setVisible(true);
                }
            }
        });

        //table history end

        try {
            String[] listLanguage = getList("data\\languagelist.txt");
            String[] listKeyLanguage1 = getList("data\\languagekeylist.txt");
            langKey = listKeyLanguage1;

            for (String item : listLanguage) {
                fromComboBox.addItem(item);
                comboBox2.addItem(item);
            }
        } catch (IOException ex) {
            infoBox("Đã xảy ra lỗi !!!", "Eror");
        }

        fromComboBox.setMaximumRowCount(10);
        comboBox2.setMaximumRowCount(10);
        fromComboBox.setSelectedIndex(21);
        comboBox2.setSelectedIndex(101);

        translateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String a = langKey[fromComboBox.getSelectedIndex()];
                String b = langKey[comboBox2.getSelectedIndex()];
                String text = editorPane1.getText();
                try {
                    String translatedText = translate(a, b, text);
                    editorPane2.setText(translatedText);
                } catch (Exception IOException) {
                    infoBox("Đã xảy ra lỗi, vui lòng nhập lại", "Eror");
                }

            }
        });


    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("APP");
        frame.setContentPane(new App().panel1);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);

    }
}
