# Custom Boat Mod Element Design

This document outlines the design for the Custom Boat mod element in the Boatload of Signs plugin.

## Properties

- **Name**: Name of the custom boat
- **Texture**: Custom texture for the boat
- **Creative Tab**: Which creative tab the boat will appear in
- **Max Stack Size**: Maximum stack size in inventory (default: 1)
- **Has Chest**: Whether the boat has a chest (creates two variants)
- **Speed Multiplier**: Boat speed multiplier (compared to vanilla boats)
- **Durability**: Boat durability (how much damage it can take before breaking)
- **Material**: Material of the boat (affects drops when broken)
- **Custom Sound**: Custom sounds for rowing, placing, and breaking
- **Buoyancy**: How well the boat floats in water

## Implementation Notes

- Will extend vanilla Boat entity functionality
- Will support both regular and chest boat variants
- Will be craftable using the material and a boat shape
- Will have the same control mechanics as vanilla boats
- Will generate appropriate item and entity models
- Will support localization
- Will allow players to enter/exit the boat like vanilla boats
- Will support damage and destruction mechanics
- Will drop appropriate items when broken
