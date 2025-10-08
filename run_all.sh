#!/usr/bin/env bash
ROOT="$(cd "$(dirname "$0")" && pwd)"
echo "Compilando y ejecutando (ejercicios base - pueden tener TODOs)..."
for d in ejercicio-1-basico ejercicio-2-condicion-carrera ejercicio-3-synchronized ejercicio-4-productor-consumidor; do
  echo "=== EJERCICIO: $d ==="
  (cd "$ROOT/$d" && if [ -f Main.java ]; then javac Main.java && java Main; else echo "No hay Main.java"; fi) || true
  echo
done
echo "FIN."
