import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Alexey Silichenko (a.silichenko@gmail.com)
 * created on 23.10.2017
 */
public class ViewController implements Initializable {

    private static final int STATIC_COLS_NUMBER = 1;
    private static final int RIGHT_PADDING = 10;

    private static final int GROUP1_SIZE = 1;
    private static final int GROUP2_SIZE = 3;
    private static final int GROUP3_SIZE = 5;
    private static final int GROUP4_SIZE = 5;
    private static final int GROUP5_SIZE = 3;

    private static final int GROUP1_OFFSET = 0;
    private static final int GROUP2_OFFSET = GROUP1_SIZE;
    private static final int GROUP3_OFFSET = GROUP2_OFFSET + GROUP2_SIZE;
    private static final int GROUP4_OFFSET = GROUP3_OFFSET + GROUP3_SIZE;
    private static final int GROUP5_OFFSET = GROUP4_OFFSET + GROUP4_SIZE;

    @FXML
    private AnchorPane root;
    @FXML
    private TableView<?> table;
    @FXML
    private TableColumn staticCol;

    @FXML
    private Line l1;
    @FXML
    private Line l2;
    @FXML
    private Line l3;
    @FXML
    private Line l4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final double padding = staticCol.getWidth() + RIGHT_PADDING;
        table.setColumnResizePolicy(param -> {
            final double totalWidth = table.getWidth() - padding;
            final double colWidth = totalWidth / (table.getColumns().size() - STATIC_COLS_NUMBER);

            placeLine(l1, colWidth, GROUP2_OFFSET);
            placeLine(l2, colWidth, GROUP3_OFFSET);
            placeLine(l3, colWidth, GROUP4_OFFSET);
            placeLine(l4, colWidth, GROUP5_OFFSET);

            double sum = 0;
            sum = resize(colWidth, sum, GROUP1_OFFSET, GROUP1_SIZE);
            sum = resize(colWidth, sum, GROUP2_OFFSET, GROUP2_SIZE);
            sum = resize(colWidth, sum, GROUP3_OFFSET, GROUP3_SIZE);
            sum = resize(colWidth, sum, GROUP4_OFFSET, GROUP4_SIZE);
            resize(colWidth, sum, GROUP5_OFFSET, GROUP5_SIZE);

            return true;
        });
    }

    private void placeLine(Line line, double colWidth, int offset) {
        line.setLayoutX(staticCol.getWidth() + colWidth * offset);
    }

    /**
     * Calculates column width as integer based on col position and col group size
     *
     * @param w0        expected column width
     * @param sum       sum of real columns width
     * @param offset    columns count group offset
     * @param groupSize number of columns in this group
     * @return new sum
     */
    private double resize(double w0, double sum, int offset, int groupSize) {
        final double groupWidth = w0 * (offset + groupSize) - sum;
        double groupSum = 0;
        for (int i = 1; i <= groupSize; i++) {
            final double w1 = Math.floor((groupWidth - groupSum) / (groupSize + 1 - i));
            groupSum += w1;
            table.getColumns().get(offset + i).setPrefWidth(w1);
        }
        return sum + groupSum;
    }

    @FXML
    public void decrease() {
        final Window window = root.getScene().getWindow();
        final double width = window.getWidth();
        window.setWidth(width - 1);
    }

    @FXML
    public void increase() {
        final Window window = root.getScene().getWindow();
        final double width = window.getWidth();
        window.setWidth(width + 1);
    }
}
