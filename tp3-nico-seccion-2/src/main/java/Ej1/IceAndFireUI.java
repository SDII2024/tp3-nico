package Ej1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;

public class IceAndFireUI extends JFrame {
    private JTextArea resultArea;
    private JComboBox<String> apiSelector;

    public IceAndFireUI() {
        setTitle("A Song of Ice and Fire API Client");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        JLabel selectLabel = new JLabel("Select API:");
        apiSelector = new JComboBox<>(new String[]{"Houses", "Characters", "Books"});
        JButton fetchButton = new JButton("Fetch");
        fetchButton.addActionListener(new FetchButtonListener());

        topPanel.add(selectLabel);
        topPanel.add(apiSelector);
        topPanel.add(fetchButton);

        resultArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(resultArea);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private class FetchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedAPI = (String) apiSelector.getSelectedItem();
            String apiUrl = "https://www.anapioficeandfire.com/api/" + selectedAPI.toLowerCase();

            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                if (selectedAPI.equals("Characters")) {
                    displayCharacterNames(response.toString());
                } else if (selectedAPI.equals("Houses")) {
                    displayHouseNames(response.toString());
                } else if (selectedAPI.equals("Books")) {
                    displayBookNames(response.toString());
                }
                connection.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();
                resultArea.setText("Error occurred: " + ex.getMessage());
            }
        }

        private void displayCharacterNames(String jsonResponse) {
            JSONArray characters = new JSONArray(jsonResponse);
            StringBuilder names = new StringBuilder();
            for (int i = 0; i < characters.length(); i++) {
                JSONObject character = characters.getJSONObject(i);
                names.append(character.getString("name")).append("\n");
            }
            resultArea.setText(names.toString());
        }

        private void displayHouseNames(String jsonResponse) {
            JSONArray houses = new JSONArray(jsonResponse);
            StringBuilder names = new StringBuilder();
            for (int i = 0; i < houses.length(); i++) {
                JSONObject house = houses.getJSONObject(i);
                names.append(house.getString("name")).append("\n");
            }
            resultArea.setText(names.toString());
        }

        private void displayBookNames(String jsonResponse) {
            JSONArray books = new JSONArray(jsonResponse);
            StringBuilder names = new StringBuilder();
            for (int i = 0; i < books.length(); i++) {
                JSONObject book = books.getJSONObject(i);
                names.append(book.getString("name")).append("\n");
            }
            resultArea.setText(names.toString());
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IceAndFireUI iceAndFireUI = new IceAndFireUI();
            iceAndFireUI.setVisible(true);
        });
    }
}