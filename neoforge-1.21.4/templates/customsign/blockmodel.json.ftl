{
  "parent": "block/block",
  "textures": {
    "particle": "${modid}:block/${registryname}",
    "front": "${modid}:block/${registryname}",
    "back": "${modid}:block/${registryname}<#if textureBack?has_content>_back</#if>"
  },
  "elements": [
    {
      "from": [0, 4.5, 7],
      "to": [16, 15.5, 9],
      "faces": {
        "north": {"uv": [0, 0, 16, 11], "texture": "#front"},
        "east": {"uv": [14, 0, 16, 11], "texture": "#front"},
        "south": {"uv": [0, 0, 16, 11], "texture": "#back"},
        "west": {"uv": [0, 0, 2, 11], "texture": "#front"},
        "up": {"uv": [0, 0, 16, 2], "texture": "#front"},
        "down": {"uv": [0, 9, 16, 11], "texture": "#front"}
      }
    },
    {
      "from": [7, 0, 7],
      "to": [9, 4.5, 9],
      "faces": {
        "north": {"uv": [7, 11, 9, 15.5], "texture": "#front"},
        "east": {"uv": [7, 11, 9, 15.5], "texture": "#front"},
        "south": {"uv": [7, 11, 9, 15.5], "texture": "#back"},
        "west": {"uv": [7, 11, 9, 15.5], "texture": "#front"}
      }
    }
  ]
}
