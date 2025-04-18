package util;

import java.util.ArrayList;
import java.util.List;

public class TableFormatter {
    private List<String> headers;
    private List<List<String>> rows;
    private List<Integer> columnWidths;

    public TableFormatter() {
        headers = new ArrayList<>();
        rows = new ArrayList<>();
        columnWidths = new ArrayList<>();
    }

    public void setHeaders(String... headers) {
        this.headers.clear();
        this.columnWidths.clear();
        for (String header : headers) {
            this.headers.add(header);
            this.columnWidths.add(header.length());
        }
    }

    public void addRow(String... values) {
        List<String> row = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            String value = values[i];
            row.add(value);
            if (i < columnWidths.size()) {
                columnWidths.set(i, Math.max(columnWidths.get(i), value.length()));
            }
        }
        rows.add(row);
    }

    public String format() {
        StringBuilder sb = new StringBuilder();
        
        // Print header
        sb.append("+");
        for (int width : columnWidths) {
            sb.append("-".repeat(width + 2)).append("+");
        }
        sb.append("\n");

        sb.append("|");
        for (int i = 0; i < headers.size(); i++) {
            String header = headers.get(i);
            sb.append(" ").append(header).append(" ".repeat(columnWidths.get(i) - header.length() + 1)).append("|");
        }
        sb.append("\n");

        sb.append("+");
        for (int width : columnWidths) {
            sb.append("-".repeat(width + 2)).append("+");
        }
        sb.append("\n");

        // Print rows
        for (List<String> row : rows) {
            sb.append("|");
            for (int i = 0; i < row.size(); i++) {
                String value = row.get(i);
                sb.append(" ").append(value).append(" ".repeat(columnWidths.get(i) - value.length() + 1)).append("|");
            }
            sb.append("\n");
        }

        // Print bottom border
        sb.append("+");
        for (int width : columnWidths) {
            sb.append("-".repeat(width + 2)).append("+");
        }
        sb.append("\n");

        return sb.toString();
    }

    public void clear() {
        headers.clear();
        rows.clear();
        columnWidths.clear();
    }
} 