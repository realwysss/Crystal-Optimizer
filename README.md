<p align="center"><img src="icon.png" width="128"></p>

# Crystal Optimizer

faster end crystal pvp on the client. vanilla mechanics stay the same, the client just stops waiting for the server.

client side fabric mod. minecraft 1.21.11, 26.1 and 26.2.

- **fast placing**: hold use with a crystal in your hand and it places every tick on the obsidian or bedrock you look at
- **fast breaking**: on high ping a crystal you hit is removed on your screen right away so the next one can be placed
- **no bounce**: the hotbar item does not jump when the server gives crystals back, and the crystal count only changes when the server says so
- **toggle**: `/crystalopt on` and `/crystalopt off`
- **server opt-out**: servers can switch it off for everyone (see below)

servers that run this mod can set `allow` to `true` in `config/crystalopt_server.json`. the default is `false`, which tells every client to switch the optimizer off.

## credits

the idea for this mod comes from Truefuly, who made the first version of it. this version is a new rewrite by wysss.

## download

| jar | minecraft |
|---|---|
| `crystal-optimizer-mc1.21.11-2.0.0.jar` | 1.21.11 |
| `crystal-optimizer-mc26.1-26.2-2.0.0.jar` | 26.1 to 26.2 |

on the releases page. fabric loader 0.16 or newer and fabric api.

## how it works

a tick hook on the player does the placing and the early removal, the normal use click is skipped while you hold a crystal, and small mixins on the crystal item and the item count stop the predicted count change and the pop animation. the server switch is a tiny packet that the server sends when you join.

all rights reserved. made by wysss.
