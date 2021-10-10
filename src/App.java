import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
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
    private JButton swapButton;
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
    public static ArrayList<String> getList2(String filePath) throws IOException {
        ArrayList<String> list = new ArrayList<String>();
        String url = filePath;
        // Đọc dữ liệu từ File với Scanner
        FileInputStream fileInputStream = new FileInputStream(url);
        Scanner scanner = new Scanner(fileInputStream);
        String ListKeys;

        try {
            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine());
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
    public static void addTodata(String FromTo, String From, String To)
    {
        try {
            FileWriter fw1 = new FileWriter("data\\FromTo.txt", true);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            PrintWriter pw1 = new PrintWriter(bw1);
            pw1.println(FromTo);
            pw1.close();

            FileWriter fw2 = new FileWriter("data\\From.txt",true);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            PrintWriter pw2 = new PrintWriter(bw2);
            pw2.println(From.replace("\n", " "));
            pw2.close();

            FileWriter fw3 = new FileWriter("data\\To.txt",true);
            BufferedWriter bw3 = new BufferedWriter(fw3);
            PrintWriter pw3 = new PrintWriter(bw3);
            pw3.println(To);
            pw3.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

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
    public void loadDataTotable()
    {
        Object[] columns = {"", "From", "To"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);

        // set the model to the table

        table1.setModel(model);

        // quy dinh bang co bao nhieu cot


        ArrayList<String> dataFromTo= new ArrayList<>();
        ArrayList<String> dataFrom= new ArrayList<>();
        ArrayList<String> dataTo= new ArrayList<>();
        try {
            dataFromTo = getList2("data\\FromTo.txt");
            dataFrom = getList2("data\\From.txt");
            dataTo = getList2("data\\To.txt");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Object[] row = new Object[3];
        for (int i = dataFromTo.size()-1;i>=0 ;i--)
        {
            row[0] = dataFromTo.get(i);
            row[1] = dataFrom.get(i);
            row[2] = dataTo.get(i);
            model.addRow(row);
        }
        // add row to the model
        model.addRow(row);
    }
    public App() {
        //table history
            loadDataTotable();


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
    // Swap button để chuyển đổi

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

        try {
            Image img = ImageIO.read(getClass().getResource("img\\replace_64px.png"));
            swapButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println("Lỗi");
        }
//        Swap button để chuyển đổi ngôn ngữ
        swapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = fromComboBox.getSelectedIndex();
                int b = comboBox2.getSelectedIndex();
                fromComboBox.setSelectedIndex(b);
                comboBox2.setSelectedIndex(a);

                String t1 = editorPane1.getText();
                String t2 = editorPane2.getText();
                editorPane1.setText(t2);
                editorPane2.setText(t1);
            }
        });
//        nhấn để dịch
        translateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(editorPane1.getText().replaceAll(" ", "").equals(""))
                {

                }
                else
                {
                    loadDataTotable();
                    String a = langKey[fromComboBox.getSelectedIndex()];
                    String b = langKey[comboBox2.getSelectedIndex()];
                    String text = editorPane1.getText();
                    try {
                        String translatedText = translate(a, b, text);
                        editorPane2.setText(translatedText);
                        String fromTo = a+"-"+b;
                        addTodata(fromTo,text,editorPane2.getText());
                    } catch (Exception IOException) {
                        infoBox("Đã xảy ra lỗi, vui lòng nhập lại", "Eror");
                    }
                }


            }
        });


    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("APP");
        frame.setContentPane(new App().panel1);
        frame.setSize(1000, 700);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);



    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
