package unitTest;

import com.snapppay.expensetracker.model.CategoryType;
import com.snapppay.expensetracker.service.BudgetAlertService;
import com.snapppay.expensetracker.service.CategorizedBillService;
import com.snapppay.expensetracker.service.CategoryBudgetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BudgetAlertServiceTest {

    @InjectMocks
    private BudgetAlertService budgetAlertService;
    @Mock
    private CategoryBudgetService categoryBudgetService;
    @Mock
    private CategorizedBillService categorizedBillService;

    @Test
    public void testGetAlertWhenTheUserHasSpentMoreThatTheBudget() {
        setUpTheUserHasSpentMoreThatTheBudget();
        var result = budgetAlertService.getBudgetAlerts("r.gholamzad");
        var alertBudget = result.stream().toList();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(new BigDecimal(10_000), alertBudget.get(0).amountSpent());
        Assertions.assertEquals(new BigDecimal(5_000), alertBudget.get(0).budgetAmount());

        verify(categoryBudgetService, times(1)).getCategoryBudget("r.gholamzad");
        verify(categorizedBillService, times(1)).getSummaryCategorizedBills(anyString(), any(), any());
    }

    @Test
    public void testGetAlertWhenTheUserHasNotSetBudget() {
        var summaryCategorizedBills = new HashMap<CategoryType, BigDecimal>();
        summaryCategorizedBills.put(CategoryType.COFFEE, new BigDecimal(10_000));
        summaryCategorizedBills.put(CategoryType.CAR, new BigDecimal(20_000));
        when(categoryBudgetService.getCategoryBudget(anyString())).thenReturn(Map.of());
        when(categorizedBillService.getSummaryCategorizedBills(anyString(), any(), any())).thenReturn(summaryCategorizedBills);

        var result = budgetAlertService.getBudgetAlerts("r.gholamzad");

        Assertions.assertEquals(0, result.size());

        verify(categoryBudgetService, times(1)).getCategoryBudget("r.gholamzad");
        verify(categorizedBillService, times(1)).getSummaryCategorizedBills(anyString(), any(), any());
    }

    @Test
    public void testGetAlertWhenTheUserDoesNotHaveBills() {
        when(categoryBudgetService.getCategoryBudget(anyString())).thenReturn(Map.of());
        when(categorizedBillService.getSummaryCategorizedBills(anyString(), any(), any())).thenReturn(Map.of());

        var result = budgetAlertService.getBudgetAlerts("r.gholamzad");

        Assertions.assertEquals(0, result.size());

        verify(categoryBudgetService, times(1)).getCategoryBudget("r.gholamzad");
        verify(categorizedBillService, times(1)).getSummaryCategorizedBills(anyString(), any(), any());
    }

    private void setUpTheUserHasSpentMoreThatTheBudget() {
        var summaryCategorizedBills = new HashMap<CategoryType, BigDecimal>();
        var categorizedBudget = new HashMap<CategoryType, BigDecimal>();

        summaryCategorizedBills.put(CategoryType.COFFEE, new BigDecimal(10_000));
        categorizedBudget.put(CategoryType.COFFEE, new BigDecimal(5_000));

        summaryCategorizedBills.put(CategoryType.CAR, new BigDecimal(20_000));
        categorizedBudget.put(CategoryType.CAR, new BigDecimal(50_000));

        summaryCategorizedBills.put(CategoryType.FOOD, new BigDecimal(100_000));

        when(categoryBudgetService.getCategoryBudget(anyString())).thenReturn(categorizedBudget);
        when(categorizedBillService.getSummaryCategorizedBills(anyString(), any(), any())).thenReturn(summaryCategorizedBills);
    }
}
