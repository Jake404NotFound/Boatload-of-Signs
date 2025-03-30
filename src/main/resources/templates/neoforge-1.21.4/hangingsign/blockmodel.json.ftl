{
  "parent": "block/block",
  "textures": {
    "particle": "${modid}:block/${registryname}",
    "front": "${modid}:block/${registryname}",
    "back": "${modid}:block/${registryname}<#if textureBack?has_content>_back</#if>",
    "chain": "${modid}:block/${registryname}<#if chainTexture?has_content>_chain<#else>_chain</#if>"
  },
  "elements": [
    {
      "from": [1, 10, 7],
      "to": [15, 16, 9],
      "faces": {
        "north": {"uv": [1, 0, 15, 6], "texture": "#front"},
        "east": {"uv": [14, 0, 16, 6], "texture": "#front"},
        "south": {"uv": [1, 0, 15, 6], "texture": "#back"},
        "west": {"uv": [0, 0, 2, 6], "texture": "#front"},
        "up": {"uv": [1, 0, 15, 2], "texture": "#front"},
        "down": {"uv": [1, 4, 15, 6], "texture": "#front"}
      }
    },
    {
      "from": [7, 0, 8],
      "to": [9, 10, 8],
      "faces": {
        "north": {"uv": [0, 0, 2, 10], "texture": "#chain"},
        "south": {"uv": [0, 0, 2, 10], "texture": "#chain"}
      }
    }
  ]
}
