# Custom Sign Mod Element Design

This document outlines the design for the Custom Sign mod element in the Boatload of Signs plugin.

## Properties

- **Name**: Name of the custom sign
- **Texture**: Custom texture for the sign (front and back)
- **Creative Tab**: Which creative tab the sign will appear in
- **Max Stack Size**: Maximum stack size in inventory (default: 16)
- **Material**: Material of the sign (wood, metal, etc.)
- **Has Gravity**: Whether the sign is affected by gravity when not attached
- **Waterloggable**: Whether the sign can be waterlogged
- **Light Level**: Light level emitted by the sign (0-15)
- **Required Tool**: Tool required to break the sign
- **Custom Sound**: Custom sound when placed/broken

## Implementation Notes

- Will extend vanilla Sign block functionality
- Will support text formatting and colors like vanilla signs
- Will be placeable on blocks and on the ground
- Will have the same interaction mechanics as vanilla signs
- Will generate appropriate item and block models
- Will support localization
