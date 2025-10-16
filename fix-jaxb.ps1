# Script de correction pour Windows PowerShell
# Exécutez ce script pour résoudre le problème JAXB

Write-Host "=== Correction du problème JAXB ===" -ForegroundColor Green
Write-Host ""

# Étape 1: Nettoyer le projet
Write-Host "Étape 1: Nettoyage du projet..." -ForegroundColor Yellow
mvn clean
if ($LASTEXITCODE -ne 0) {
    Write-Host "Erreur lors du nettoyage!" -ForegroundColor Red
    exit 1
}
Write-Host "✓ Nettoyage réussi" -ForegroundColor Green
Write-Host ""

# Étape 2: Forcer le téléchargement des dépendances
Write-Host "Étape 2: Téléchargement des dépendances..." -ForegroundColor Yellow
mvn dependency:resolve -U
if ($LASTEXITCODE -ne 0) {
    Write-Host "Erreur lors du téléchargement des dépendances!" -ForegroundColor Red
    exit 1
}
Write-Host "✓ Dépendances téléchargées" -ForegroundColor Green
Write-Host ""

# Étape 3: Vérifier que JAXB est bien téléchargé
Write-Host "Étape 3: Vérification de JAXB..." -ForegroundColor Yellow
mvn dependency:tree | Select-String "jaxb"
Write-Host ""

# Étape 4: Compiler le projet
Write-Host "Étape 4: Compilation du projet..." -ForegroundColor Yellow
mvn compile
if ($LASTEXITCODE -ne 0) {
    Write-Host "Erreur lors de la compilation!" -ForegroundColor Red
    exit 1
}
Write-Host "✓ Compilation réussie" -ForegroundColor Green
Write-Host ""

# Étape 5: Lancer l'application
Write-Host "Étape 5: Lancement de l'application..." -ForegroundColor Yellow
Write-Host "Appuyez sur Ctrl+C pour arrêter l'application" -ForegroundColor Cyan
Write-Host ""
mvn spring-boot:run
