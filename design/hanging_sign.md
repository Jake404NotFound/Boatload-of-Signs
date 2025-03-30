# Hanging Sign Mod Element Design

This document outlines the design for the Hanging Sign mod element in the Boatload of Signs plugin.

## Properties

- **Name**: Name of the custom hanging sign
- **Texture**: Custom texture for the hanging sign (front and back)
- **Creative Tab**: Which creative tab the hanging sign will appear in
- **Max Stack Size**: Maximum stack size in inventory (default: 16)
- **Material**: Material of the hanging sign (wood, metal, etc.)
- **Chain Texture**: Optional custom texture for the chains
- **Waterloggable**: Whether the hanging sign can be waterlogged
- **Light Level**: Light level emitted by the hanging sign (0-15)
- **Required Tool**: Tool required to break the hanging sign
- **Custom Sound**: Custom sound when placed/broken

## Implementation Notes

- Will extend vanilla Hanging Sign block functionality
- Will support text formatting and colors like vanilla hanging signs
- Will be placeable on ceilings and walls with chains
- Will have the same interaction mechanics as vanilla hanging signs
- Will generate appropriate item and block models
- Will support localization
