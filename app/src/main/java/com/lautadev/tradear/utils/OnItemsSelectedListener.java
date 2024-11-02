package com.lautadev.tradear.utils;

import com.lautadev.tradear.dto.ItemDTO;

import java.util.List;

public interface OnItemsSelectedListener {
    void onItemsSelected(List<ItemDTO> selectedItems);
}
