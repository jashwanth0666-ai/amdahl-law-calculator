import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;

public class AmdahlSpeedupAnalyzer extends JFrame {

    private JTextField serialField;
    private JTextField parallelField;

    private JTable speedupTable;
    private JTextArea examplesArea;
    private JLabel maxSpeedupLabel;

    private SpeedupGraph graphPanel;

    private final DecimalFormat df = new DecimalFormat("0.0000");

    // Required processor counts
    private final int[] processors = {1, 2, 4, 8, 16};

    public AmdahlSpeedupAnalyzer() {

        setTitle("Amdahl's Law Calculator and Parallel Speedup Analyzer");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
    }

    private void createUI() {

        // -----------------------------
        // TITLE
        // -----------------------------

        JLabel title = new JLabel(
                "Amdahl's Law Calculator and Parallel Speedup Analyzer",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        // -----------------------------
        // INPUT PANEL
        // -----------------------------

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(
                BorderFactory.createTitledBorder("Input Parameters")
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel serialLabel = new JLabel("Serial Fraction (S):");
        JLabel parallelLabel = new JLabel("Parallel Fraction (P):");

        serialField = new JTextField("0.10", 10);
        parallelField = new JTextField("0.90", 10);

        JButton calculateButton = new JButton("Calculate Speedup");

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(serialLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(serialField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(parallelLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(parallelField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        inputPanel.add(calculateButton, gbc);

        calculateButton.addActionListener(e -> calculate());

        // -----------------------------
        // FORMULA PANEL
        // -----------------------------

        JPanel formulaPanel = new JPanel();
        formulaPanel.setLayout(new BoxLayout(formulaPanel, BoxLayout.Y_AXIS));

        formulaPanel.setBorder(
                BorderFactory.createTitledBorder("Amdahl's Law")
        );

        JLabel formula = new JLabel(
                "Speedup = 1 / (S + P / N)"
        );

        JLabel explanation = new JLabel(
                "S = Serial Fraction     P = Parallel Fraction     N = Number of Processors"
        );

        JLabel relationship = new JLabel(
                "S + P = 1"
        );

        formula.setFont(new Font("Arial", Font.BOLD, 16));

        formulaPanel.add(formula);
        formulaPanel.add(Box.createVerticalStrut(5));
        formulaPanel.add(explanation);
        formulaPanel.add(Box.createVerticalStrut(5));
        formulaPanel.add(relationship);

        // -----------------------------
        // TOP PANEL
        // -----------------------------

        JPanel topPanel = new JPanel(new BorderLayout());

        topPanel.add(inputPanel, BorderLayout.WEST);
        topPanel.add(formulaPanel, BorderLayout.CENTER);

        // -----------------------------
        // SPEEDUP TABLE
        // -----------------------------

        String[] columns = {
                "Processors (N)",
                "Serial Fraction (S)",
                "Parallel Fraction (P)",
                "Theoretical Speedup"
        };

        speedupTable = new JTable(
                new DefaultTableModel(columns, 0)
        );

        speedupTable.setRowHeight(25);

        JScrollPane tableScroll = new JScrollPane(speedupTable);

        tableScroll.setBorder(
                BorderFactory.createTitledBorder("Speedup Table")
        );

        // -----------------------------
        // GRAPH
        // -----------------------------

        graphPanel = new SpeedupGraph();

        graphPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Processor Count vs Theoretical Speedup"
                )
        );

        // -----------------------------
        // MAX SPEEDUP
        // -----------------------------

        maxSpeedupLabel = new JLabel(
                "Maximum Theoretical Speedup: -"
        );

        maxSpeedupLabel.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        JPanel resultPanel = new JPanel(new BorderLayout());

        resultPanel.setBorder(
                BorderFactory.createTitledBorder("Maximum Theoretical Speedup")
        );

        resultPanel.add(
                maxSpeedupLabel,
                BorderLayout.CENTER
        );

        // -----------------------------
        // WORKED EXAMPLES
        // -----------------------------

        examplesArea = new JTextArea();

        examplesArea.setEditable(false);
        examplesArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JScrollPane examplesScroll =
                new JScrollPane(examplesArea);

        examplesScroll.setBorder(
                BorderFactory.createTitledBorder(
                        "Worked Numerical Examples"
                )
        );

        // -----------------------------
        // TABS
        // -----------------------------

        JTabbedPane tabs = new JTabbedPane();

        JPanel analysisPanel = new JPanel(new GridLayout(1, 2));

        analysisPanel.add(tableScroll);
        analysisPanel.add(graphPanel);

        tabs.addTab("Speedup Analysis", analysisPanel);
        tabs.addTab("Worked Examples", examplesScroll);

        // -----------------------------
        // MAIN LAYOUT
        // -----------------------------

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        mainPanel.add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(tabs, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(resultPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // Initial calculation
        calculate();
    }

    // ============================================================
    // AMDAHL'S LAW CALCULATION
    // ============================================================

    private double calculateSpeedup(
            double serial,
            double parallel,
            int numberOfProcessors
    ) {

        return 1.0 /
                (serial + (parallel / numberOfProcessors));
    }

    // ============================================================
    // MAIN CALCULATION
    // ============================================================

    private void calculate() {

        try {

            double serial =
                    Double.parseDouble(
                            serialField.getText().trim()
                    );

            double parallel =
                    Double.parseDouble(
                            parallelField.getText().trim()
                    );

            // Validate fractions
            if (serial < 0 || parallel < 0) {

                showError(
                        "Serial and parallel fractions cannot be negative."
                );

                return;
            }

            // Check S + P = 1
            if (Math.abs((serial + parallel) - 1.0) > 0.000001) {

                showError(
                        "Serial fraction + Parallel fraction must equal 1."
                );

                return;
            }

            // -----------------------------
            // UPDATE TABLE
            // -----------------------------

            DefaultTableModel model =
                    (DefaultTableModel) speedupTable.getModel();

            model.setRowCount(0);

            double[] speedups =
                    new double[processors.length];

            for (int i = 0; i < processors.length; i++) {

                int n = processors[i];

                double speedup =
                        calculateSpeedup(
                                serial,
                                parallel,
                                n
                        );

                speedups[i] = speedup;

                model.addRow(
                        new Object[]{
                                n,
                                df.format(serial),
                                df.format(parallel),
                                df.format(speedup)
                        }
                );
            }

            // -----------------------------
            // MAXIMUM THEORETICAL SPEEDUP
            // -----------------------------

            double maxSpeedup;

            if (serial == 0) {

                maxSpeedup =
                        Double.POSITIVE_INFINITY;

                maxSpeedupLabel.setText(
                        "Maximum Theoretical Speedup: Unlimited"
                );

            } else {

                maxSpeedup =
                        1.0 / serial;

                maxSpeedupLabel.setText(
                        "Maximum Theoretical Speedup = 1 / S = "
                                + df.format(maxSpeedup)
                );
            }

            // -----------------------------
            // UPDATE GRAPH
            // -----------------------------

            graphPanel.setData(
                    processors,
                    speedups,
                    maxSpeedup
            );

            // -----------------------------
            // WORKED EXAMPLES
            // -----------------------------

            generateWorkedExamples(
                    serial,
                    parallel
            );

        } catch (NumberFormatException e) {

            showError(
                    "Please enter valid numerical values."
            );
        }
    }

    // ============================================================
    // WORKED NUMERICAL EXAMPLES
    // ============================================================

    private void generateWorkedExamples(
            double serial,
            double parallel
    ) {

        StringBuilder sb = new StringBuilder();

        sb.append(
                "Amdahl's Law:\n"
        );

        sb.append(
                "Speedup = 1 / (S + P/N)\n\n"
        );

        for (int i = 0; i < processors.length; i++) {

            int n = processors[i];

            double speedup =
                    calculateSpeedup(
                            serial,
                            parallel,
                            n
                    );

            sb.append(
                    "Example "
                            + (i + 1)
                            + ": N = "
                            + n
                            + " processors\n"
            );

            sb.append(
                    "S = "
                            + df.format(serial)
                            + ", P = "
                            + df.format(parallel)
                            + "\n"
            );

            sb.append(
                    "Speedup = 1 / ("
                            + df.format(serial)
                            + " + "
                            + df.format(parallel)
                            + " / "
                            + n
                            + ")\n"
            );

            sb.append(
                    "Speedup = "
                            + df.format(speedup)
                            + "\n"
            );

            sb.append(
                    "------------------------------------------\n"
            );
        }

        // Maximum theoretical speedup
        if (serial != 0) {

            double maximum =
                    1.0 / serial;

            sb.append("\nMaximum Theoretical Speedup:\n");

            sb.append(
                    "As N approaches infinity:\n"
            );

            sb.append(
                    "Maximum Speedup = 1 / S\n"
            );

            sb.append(
                    "Maximum Speedup = 1 / "
                            + df.format(serial)
                            + "\n"
            );

            sb.append(
                    "Maximum Speedup = "
                            + df.format(maximum)
                            + "\n"
            );
        }

        sb.append(
                "\nDiminishing Returns:\n"
        );

        sb.append(
                "Increasing the number of processors increases "
                        + "speedup, but the improvement becomes smaller "
                        + "because the serial portion cannot be parallelized."
        );

        examplesArea.setText(
                sb.toString()
        );
    }

    // ============================================================
    // ERROR MESSAGE
    // ============================================================

    private void showError(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Input Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // ============================================================
    // GRAPH PANEL
    // ============================================================

    class SpeedupGraph extends JPanel {

        private int[] processorData;
        private double[] speedupData;
        private double maximumSpeedup;

        public void setData(
                int[] processors,
                double[] speedups,
                double maximum
        ) {

            this.processorData =
                    processors.clone();

            this.speedupData =
                    speedups.clone();

            this.maximumSpeedup =
                    maximum;

            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            if (processorData == null ||
                    speedupData == null) {

                return;
            }

            Graphics2D g2 =
                    (Graphics2D) g;

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int width =
                    getWidth();

            int height =
                    getHeight();

            int left =
                    70;

            int right =
                    30;

            int top =
                    40;

            int bottom =
                    60;

            int graphWidth =
                    width - left - right;

            int graphHeight =
                    height - top - bottom;

            // -----------------------------
            // FIND MAXIMUM GRAPH VALUE
            // -----------------------------

            double maxValue =
                    0;

            for (double value : speedupData) {

                if (value > maxValue) {

                    maxValue = value;
                }
            }

            if (Double.isFinite(maximumSpeedup)
                    && maximumSpeedup > maxValue) {

                maxValue =
                        maximumSpeedup;
            }

            maxValue =
                    Math.ceil(maxValue + 1);

            // -----------------------------
            // AXES
            // -----------------------------

            g2.drawLine(
                    left,
                    top,
                    left,
                    height - bottom
            );

            g2.drawLine(
                    left,
                    height - bottom,
                    width - right,
                    height - bottom
            );

            // -----------------------------
            // Y-AXIS LABELS
            // -----------------------------

            for (int i = 0; i <= 5; i++) {

                double value =
                        (maxValue / 5) * i;

                int y =
                        height
                                - bottom
                                - (int)
                                ((value / maxValue)
                                        * graphHeight);

                g2.drawString(
                        df.format(value),
                        15,
                        y + 5
                );

                g2.drawLine(
                        left - 5,
                        y,
                        left + graphWidth,
                        y
                );
            }

            // -----------------------------
            // PLOT POINTS
            // -----------------------------

            int previousX = 0;
            int previousY = 0;

            for (int i = 0;
                 i < processorData.length;
                 i++) {

                int x =
                        left
                                + (i * graphWidth)
                                / (processorData.length - 1);

                int y =
                        height
                                - bottom
                                - (int)
                                ((speedupData[i]
                                        / maxValue)
                                        * graphHeight);

                // Connect points
                if (i > 0) {

                    g2.drawLine(
                            previousX,
                            previousY,
                            x,
                            y
                    );
                }

                // Point
                g2.fillOval(
                        x - 5,
                        y - 5,
                        10,
                        10
                );

                // Processor label
                g2.drawString(
                        String.valueOf(
                                processorData[i]
                        ),
                        x - 8,
                        height - bottom + 25
                );

                // Speedup value
                g2.drawString(
                        df.format(
                                speedupData[i]
                        ),
                        x - 20,
                        y - 10
                );

                previousX = x;
                previousY = y;
            }

            // -----------------------------
            // GRAPH LABELS
            // -----------------------------

            g2.drawString(
                    "Number of Processors",
                    width / 2 - 60,
                    height - 15
            );

            g2.rotate(
                    -Math.PI / 2
            );

            g2.drawString(
                    "Theoretical Speedup",
                    -height / 2 - 50,
                    20
            );

            g2.rotate(
                    Math.PI / 2
            );
        }
    }

    // ============================================================
    // MAIN METHOD
    // ============================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            AmdahlSpeedupAnalyzer app =
                    new AmdahlSpeedupAnalyzer();

            app.setVisible(true);
        });
    }
}