package Ej2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;

public class PokemonUI extends JFrame {
    private JTextField pokemonNameField;
    private JTextArea resultArea;

    public PokemonUI() {
        setTitle("Pokémon Info");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        JLabel nameLabel = new JLabel("Enter Pokémon Name:");
        pokemonNameField = new JTextField(20);
        JButton fetchButton = new JButton("Fetch");
        fetchButton.addActionListener(new FetchButtonListener());

        topPanel.add(nameLabel);
        topPanel.add(pokemonNameField);
        topPanel.add(fetchButton);

        resultArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(resultArea);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private class FetchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String pokemonName = pokemonNameField.getText().trim().toLowerCase();
            if (!pokemonName.isEmpty()) {
                String apiUrl = "https://pokeapi.co/api/v2/pokemon/" + pokemonName;

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

                    displayPokemonInfo(response.toString());
                    connection.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    resultArea.setText("Error occurred: " + ex.getMessage());
                }
            } else {
                resultArea.setText("Please enter a Pokémon name.");
            }
        }

        private void displayPokemonInfo(String jsonResponse) {
            JSONObject pokemon = new JSONObject(jsonResponse);
            String name = pokemon.getString("name");
            int id = pokemon.getInt("id");
            JSONArray types = pokemon.getJSONArray("types");
            StringBuilder typeNames = new StringBuilder();
            for (int i = 0; i < types.length(); i++) {
                JSONObject type = types.getJSONObject(i).getJSONObject("type");
                typeNames.append(type.getString("name")).append(", ");
            }
            typeNames.delete(typeNames.length() - 2, typeNames.length()); // Remove trailing comma and space

            resultArea.setText("Name: " + name + "\n" +
                    "ID: " + id + "\n" +
                    "Types: " + typeNames.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PokemonUI pokemonUI = new PokemonUI();
            pokemonUI.setVisible(true);
        });
    }
}