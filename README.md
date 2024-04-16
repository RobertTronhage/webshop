Collaboration with Magnus Nording

School project using SSR with focus on Java spring web, JPA, and Thymeleaf.

Instructions on project in swedish:


Uppgiftsbeskrivning

Bygg en webshop från scratch utan att använda något färdigt web-shop-ramverk.

Det finns möjlighet att välja en annan uppgift som examination om man så skulle vilja. Den skall då ha en motsvarande komplexitet och använda motsvarande teknik som behövs för en webshop. En beskrivning av uppgiften skall upprättas och godkännas av utbildare innan arbetet påbörjas.


Minimala funktionella krav
Kunden skall mötas av en login-sida där hen kan registrera sig som ny kund eller logga in som tidigare registrerad.
Efter inloggning skall kunden bli föreslagen kategorier av varor, samt en sökfunktion på produktnamn.
En produktlista skall presenteras och kunden skall kunna lägga varor i sin varukorg och då ange antalet exemplar av varan.
Varukorgen skall kunna uppdateras med ändring av antal respektive borttagning av en viss vara. Varukorgen skall visa totalsumman av alla varors pris.
När kunden har fyllt varukorgen skall den kunna checkas ut och kunden skall få en bekräftelsesida som visar den lagda ordern. Denna order har också lagrats i databasen så webshoppens lagerpersonal kan börja expediera den.
Webshoppens personal skall ha en egen fast administratörsinloggning via vilken de skall kunna se websidor för oexpedierade order, expedierade order, markera en order som expedierad samt lägga till nya produkter i produktregistret.
Tester skall finnas för nyckelkomponenter, t ex varukorg. De behöver inte vara heltäckande, bara några stycken. Det viktigaste är att komponenten i fråga är testbar.
Mängden info i kund-, produkt-, varukorg- och order-objekt etc. behöver inte vara så utförlig som i en realistisk webshop.
Saker som borde finnas i en riktig webshop men som inte behöver implementeras i denna uppgift: betalning, säkerhet, snygga websidor med lull-lull. Är du osäker på något mer, fråga!


Icke-funktionella krav
Webshopen skall byggas med Java Enterprise-teknologi.
Koden skall vara noggrant separerad i traditionella skikt för utseende, controllers, tjänster, modell/business-objekt, Entities/datalagring etc.
Datalagringen skall använda JPA, Spring Data rekommenderas.
Dynamiska websidor får genereras med valfri teknik, SpringMVC/Thymeleaf rekommenderas.
Applikationen får implementeras i valfri enterprise-miljö, Spring Boot rekommenderas.
Det är inte förbjudet att använda Javascript-teknologi, men eftersom kursen är Java, är det klokt att fokusera på server-side JavaEE-teknologi.
