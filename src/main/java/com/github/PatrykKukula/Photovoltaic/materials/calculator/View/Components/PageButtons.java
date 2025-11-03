package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Components;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.PagingConstants;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

@Slf4j
public class PageButtons<T> extends HorizontalLayout {
    private final Page<T> items;
    private final Runnable renderPage;
    private Button nextButton;
    private Button previousbutton;
    private final Span pagesSpan = new Span();
    private Span pageNoSpan;
    private int pageNo = PagingConstants.PAGE_NO;
    private final int totalPages;

    public PageButtons(Page<T> items, Runnable renderPage){
        this.items = items;
        this.renderPage = renderPage;
        totalPages = items.getTotalPages();
        setUpNextButton();
        setUpPreviousButton();
        setUpPagesSpan();

        add(pagesSpan);
    }
    public int getCurrentPage(){
        return pageNo;
    }
    private void setUpNextButton(){
        nextButton = new Button(VaadinIcon.ARROW_RIGHT.create());
        nextButton.setEnabled(pageNo + 1 < totalPages);
        nextButton.addClickListener(e -> {
            pageNo++;
            renderPage.run();
            nextButton.setEnabled(pageNo + 1 < totalPages);
            previousbutton.setEnabled(pageNo > 0);
            updatePageDisplay();
        });
    }
    private void setUpPreviousButton(){
        previousbutton = new Button(VaadinIcon.ARROW_LEFT.create());
        previousbutton.setEnabled(pageNo > 0);
        previousbutton.addClickListener(e -> {
            pageNo--;
            renderPage.run();
            nextButton.setEnabled(pageNo + 1 < totalPages);
            previousbutton.setEnabled(pageNo > 0);
            updatePageDisplay();
        });
    }
    private void setUpPagesSpan(){
        String currentPage = totalPages > 0 ? String.valueOf(pageNo + 1)  : String.valueOf(pageNo);
        pageNoSpan = new Span(currentPage + "/" + totalPages);
        pagesSpan.add(previousbutton, pageNoSpan, nextButton);
        pagesSpan.getStyle().set("margin", "auto");
    }
    private void updatePageDisplay() {
        pageNoSpan.setText((pageNo + 1) + "/" + totalPages);
    }
}
