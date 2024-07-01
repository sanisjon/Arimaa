# Arimaa

Arimaa je strategická desková hra inspirovaná šachy, kterou jsem vytvořil jako svůj semestrální projekt. Tento projekt je napsaný v jazyce Java s využitím JavaFX pro grafické uživatelské rozhraní (GUI) a je postaven na architektuře Model-View-Controller (MVC).

## Obsah

- [Požadavky](#požadavky)
- [Instalace](#instalace)
- [Použití](#použití)
- [Architektura](#architektura)
- [Autoři](#autoři)
- [Licence](#licence)

## Požadavky

- Java Development Kit (JDK) 11 nebo vyšší
- JavaFX SDK
- Git (pro klonování repozitáře)

## Instalace

1. Naklonujte repozitář do svého lokálního stroje:

    ```bash
    git clone https://gitlab.fel.cvut.cz/B222_B0B36PJV/sanisjon.git
    ```

2. Importujte projekt do své oblíbené IDE (např. IntelliJ IDEA nebo Eclipse).

3. Ujistěte se, že máte správně nakonfigurovanou cestu k JavaFX SDK.

## Použití

1. Spusťte hlavní třídu projektu, obvykle nazvanou `Play.java`.

2. Po spuštění aplikace se otevře okno s grafickým uživatelským rozhraním hry Arimaa.

3. Hru lze ovládat pomocí myši a nabízených tlačítek v GUI.

## Architektura

Projekt je strukturován podle architektury Model-View-Controller (MVC):

- **Model**: Obsahuje logiku hry, pravidla a stav hry.
- **View**: Zajišťuje grafické uživatelské rozhraní pomocí JavaFX.
- **Controller**: Spojuje Model a View, zpracovává uživatelské vstupy a aktualizuje zobrazení podle změn v modelu.

## Autoři

- **Jonáš Sanislo** - hlavní vývojář - sanisjon (https://github.com/sanisjone)

## Licence

Tento projekt je licencován pod licencí MIT - podrobnosti naleznete v souboru LICENSE.
