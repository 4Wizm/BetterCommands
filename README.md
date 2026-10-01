# BetterCommands

**BetterCommands** è un plugin leggero e potente che permette di **bloccare qualsiasi comando** su server Spigot/Paper e proxy BungeeCord/Waterfall. Tutto è configurabile tramite un semplice file `config.yml` — nessuna ricompilazione necessaria.

Perfetto per server che vogliono impedire a giocatori senza permessi di usare comandi pericolosi come `/op`, `/stop`, `/ban` e molti altri.

---

## ✨ Caratteristiche

- 🚫 **Blocco comandi** illimitato tramite config
- 🎯 **Whitelist comandi** consentiti (priorità massima)
- 🎨 **Supporto colori HEX** (`&#FF0000`) su 1.16+ e legacy (`&c`, `&l`)
- 🔒 **Port Bypass Prevention** — impedisce connessioni dirette al server backend
- 🛡️ **Protezione tab-complete** — i comandi bloccati non appaiono nel Tab
- ⚡ **Prestazioni ottimizzate** — `HashSet` + regex pre-compilate + reflection cachata
- 🔄 **Reload a caldo** con `/bettercommands reload` (permesso `bettercommands.use.reload`)
- 💬 **Messaggi personalizzabili** con placeholder `%player%`, `%command%`, `%ip%`

---

## 🎮 Compatibilità

| Piattaforma | Supporto |
|---|---|
| Spigot | ✅ 1.8.8 → 1.21+ |
| Paper | ✅ 1.8.8 → 1.21+ |
| Purpur / Pufferfish / Airplane | ✅ Sì |
| BungeeCord | ✅ Sì |
| Waterfall | ✅ Sì |
| Velocity | ❌ Non supportato |

| Java | Supporto |
|---|---|
| Java 8 | ✅ |
| Java 11 | ✅ |
| Java 17 | ✅ |
| Java 21 | ✅ |

---

## 📥 Installazione

1. Scarica l'ultima release dalla sezione [Releases](../../releases)
2. Copia `BetterCommands-1.0.0.jar` nella cartella `plugins/` del tuo server
3. Avvia il server
4. Modifica `plugins/BetterCommands/config.yml`
5. Usa `/bettercommands reload` per applicare le modifiche

---

## ⚙️ Configurazione

```yaml
blocked-commands:
  - "op"
  - "deop"
  - "stop"
  - "reload"
  - "save-all"
  - "save-off"
  - "save-on"
  - "whitelist"
  - "seed"
  - "ban"

allowed-commands:
  - "help"
  - "spawn"

blocked-message: "&c&l! &7Non hai il permesso di eseguire questo comando!"

port-bypass-prevention:
  enabled: false
  proxy-ip: "127.0.0.1"
  kick-message: "&c&l! &7Accesso diretto non consentito. Usa l'indirizzo del proxy!"
```

### Pattern supportati

| Pattern | Esempio | Descrizione |
|---|---|---|
| Comando esatto | `"op"` | Blocca solo `/op` |
| Namespace wildcard | `"bukkit:*"` | Blocca tutti i `/bukkit:xxx` |
| Regex | `"regex:ban(ip\|list)?"` | Blocca `/ban`, `/banip`, `/banlist` |

> ⚠️ I comandi vanno scritti **senza** slash iniziale.

---

## 🔑 Permessi

| Permesso | Default | Descrizione |
|---|---|---|
| `bettercommands.bypass` | OP | Permette di eseguire comandi bloccati |
| `bettercommands.use.reload` | OP | Permette di ricaricare la configurazione |
| `bettercommands.notify` | OP | Riservato per usi futuri |

---

## 💬 Comandi

| Comando | Alias | Descrizione |
|---|---|---|
| `/bettercommands reload` | `/bc reload` | Ricarica la configurazione |

---

## 🎨 Colori supportati

**Legacy (tutte le versioni):**
```
&c &a &e &b &d &f &0 &1 &2 &3 &4 &5 &6 &7 &8 &9
&l (grassetto)  &o (corsivo)  &n (sottolineato)
&m (barrato)    &k (magico)   &r (reset)
```

**HEX (solo 1.16+):**
```
&#FF0000  → Rosso
&#00FF00  → Verde
&#0099FF  → Azzurro
```

---

## 🛡️ Port Bypass Prevention

Se il tuo server è dietro un proxy BungeeCord/Waterfall, attiva questa protezione per **impedire che i giocatori si connettano direttamente alla porta del server backend**, bypassando il proxy.

```yaml
port-bypass-prevention:
  enabled: true
  proxy-ip: "IP_DEL_TUO_PROXY"
  kick-message: "&cAccesso diretto non consentito!"
```

> 💡 **Nota:** di default è **disattivato**. Attivalo solo se usi un proxy.

---

## 📸 Screenshot

> *Aggiungi qui i tuoi screenshot del plugin in azione*

---

## 🏗️ Compilazione da sorgente

```bash
git clone https://github.com/TUO-USERNAME/BetterCommands.git
cd BetterCommands
mvn clean package
```

Il JAR compilato sarà in `target/BetterCommands-1.0.0.jar`.

**Requisiti:**
- Java JDK 8 o superiore
- Apache Maven 3.6+

---

## 🤝 Contributi

I contributi sono benvenuti! Sentiti libero di:
- Aprire una **Issue** per bug o suggerimenti
- Fare un **Fork** e inviare una **Pull Request**
- Lasciare una ⭐ se il plugin ti è utile

---

## 📜 Licenza

Questo progetto è rilasciato sotto licenza **MIT**. Vedi il file [LICENSE](LICENSE) per i dettagli.

---

## 👤 Autore

**4Wizm**

- GitHub: [@4Wizm](https://github.com/4Wizm)
- Progetto: [BetterCommands](https://github.com/4Wizm/BetterCommands)

---

<p align="center">
  <b>⭐ Se questo plugin ti è stato utile, lascia una stella su GitHub! ⭐</b>
</p>
