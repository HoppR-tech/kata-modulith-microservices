# Getting Started

# Exercice 1: modulariser un monolithe existant

- Séparer la commande (order) du stock (inventory) dans des packages différents.
- Le stock se décrémente quand la commande est passée.

## Objectif
- Faire émerger les bounded contexts
- Rapatrier le vocabulaire dans chaque BC

# Exercice 2: réduire le couplage des modules

- Décoreller order du InventoryService
- Inverser les dépendances pour réduire le stock
- Poser des règles d’architecture avec ArchUnit

## Objectif
- Réduire le couplage entre des contextes différents en introduisant des événements
- Contraindre l’architecture logicielle pour préserver la cohérence des contextes

# Exercice 3: découper le monolithe en microservices

- Créer un microservice “order” et un microservice “inventory”
- Tester les microservices de manière isolée

## Objectif
- Dissocier les bounded contextes

# Exercice 4: rétablir une communication inter-services

- Créer un adaptateur pour changer l’implémentation de l’event bus (Spring vers RabbitMQ)
- Eteindre le service “inventory”
- Passer des commandes
- Rallumer le service
- Observer que les stocks sont bien mis à jour

## Objectif:
- Rétablir la communication des services en ajoutant un event bus
- Démontrer la résilience du système en cas de coupure
