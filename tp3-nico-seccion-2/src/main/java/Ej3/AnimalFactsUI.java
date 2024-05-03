package Ej3;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;

public class AnimalFactsUI extends JFrame {
    private JTextArea resultArea;
    private JComboBox<String> apiSelector;

    public AnimalFactsUI() {
        setTitle("Animal Facts");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        JLabel selectLabel = new JLabel("Select API:");
        apiSelector = new JComboBox<>(new String[]{"Cat Facts", "Dog Breeds", "Pet Food Names"});
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
            String apiUrl = "";
            if (selectedAPI.equals("Cat Facts")) {
                apiUrl = "https://cat-fact.herokuapp.com/facts/";
                displayCatFacts(apiUrl);
            } else if (selectedAPI.equals("Dog Breeds")) {
                apiUrl = "https://dog.ceo/api/breeds/list/all";
                displayDogBreeds(apiUrl);
            } else if (selectedAPI.equals("Pet Food Names")) {
                apiUrl = "https://world.openpetfoodfacts.org/api/v0/product/20106836.json";
                displayPetFoodNames(apiUrl);
            }
        }

        private void displayCatFacts(String apiUrl) {
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

                JSONArray factsArray = new JSONArray(response.toString());
                StringBuilder catFacts = new StringBuilder();
                for (int i = 0; i < factsArray.length(); i++) {
                    JSONObject factObj = factsArray.getJSONObject(i);
                    catFacts.append("Fact " + (i + 1) + ": " + factObj.getString("text")).append("\n");
                }
                resultArea.setText(catFacts.toString());
                connection.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();
                resultArea.setText("Error occurred: " + ex.getMessage());
            }
        }

        private void displayDogBreeds(String apiUrl) {
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

                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONObject message = jsonResponse.getJSONObject("message");
                StringBuilder dogBreeds = new StringBuilder();
                for (String breed : message.keySet()) {
                    dogBreeds.append(breed).append("\n");
                }
                resultArea.setText(dogBreeds.toString());
                connection.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();
                resultArea.setText("Error occurred: " + ex.getMessage());
            }
        }

        private void displayPetFoodNames(String apiUrl) {
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

                JSONObject product = new JSONObject(response.toString()).getJSONObject("product");
                String productName = product.getString("product_name");
                String brand = product.getString("brands");
                String category = product.getString("categories");

                StringBuilder details = new StringBuilder();
                details.append("Product Name: ").append(productName).append("\n")
                        .append("Brand: ").append(brand).append("\n")
                        .append("Category: ").append(category).append("\n");

                resultArea.setText(details.toString());
                connection.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();
                resultArea.setText("Error occurred: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AnimalFactsUI animalFactsUI = new AnimalFactsUI();
            animalFactsUI.setVisible(true);
        });
    }
}