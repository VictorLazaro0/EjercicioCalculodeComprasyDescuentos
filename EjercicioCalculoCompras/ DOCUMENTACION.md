# Documento Técnico: Verificación y Pruebas del Módulo de Comercio Electrónico (`PurchaseCalculator`)

## 1. Descripción del problema
El sistema de comercio electrónico realiza cálculos críticos relacionados con la gestión de carteras de compras y transacciones de usuarios, específicamente:
* **Cálculo del subtotal:** Multiplica el precio unitario de un producto por la cantidad solicitada.
* **Validación de compras:** Determina si una transacción es financieramente viable y admisible en función de valores válidos (positivos) para precios y cantidades.
* **Cálculo de descuentos y precios finales:** Aplica reglas de bonificación porcentual cuando el volumen de compra supera o iguala ciertos umbrales establecidos.

**¿Por qué es importante verificarlos?**  
Verificar estos cálculos mediante pruebas automatizadas es fundamental para evitar pérdidas financieras por errores de redondeo o lógica, prevenir fallos en la experiencia de usuario (cobros erróneos o transacciones inválidas aceptadas), y asegurar la integridad de los datos en el flujo de pago de la aplicación.

---

## 2. Reglas de negocio
* **Cálculo del subtotal:** El subtotal se obtiene multiplicando el precio del producto por la cantidad (`precio * cantidad`). Si alguno de los valores es cero, el subtotal devuelto debe ser cero.
* **Validez de una compra:** Se permite una compra únicamente si tanto el precio como la cantidad son estrictamente mayores a cero (`precio > 0` y `cantidad > 0`). Precios en cero, negativos o cantidades nulas invalidan la operación.
* **Aplicación de descuento:** Se aplica un descuento del 10% (`subtotal * 0.10`) únicamente cuando el subtotal es mayor o igual a $1,000. Si el subtotal es inferior a dicho umbral, el descuento aplicado es de $0.0$.
* **Precio final:** El precio final corresponde al resultado de restar el descuento calculado al subtotal original.

---

## 3. Casos de prueba
A partir de la clase de pruebas (`PurchaseCalculatorTest`), se definen los siguientes casos de prueba evaluados:

| Caso de Prueba | Datos de Entrada | Resultado Esperado | Resultado Obtenido | Propósito del Caso |
| :--- | :--- | :--- | :--- | :--- |
| **Subtotal estándar** | Precio: $100.0, Cantidad: 3 | 300.0 | Exitoso | Validar el cálculo correcto con valores positivos estándar. |
| **Subtotal con precio cero** | Precio: $0.0, Cantidad: 1 | 0.0 | Exitoso | Comprobar el comportamiento del sistema ante un producto sin costo. |
| **Subtotal con cantidad cero** | Precio: $30.0, Cantidad: 0 | 0.0 | Exitoso | Verificar que una cantidad nula devuelva un subtotal de cero. |
| **Compra válida** | Precio: $100.0, Cantidad: 20 | `true` | Exitoso | Asegurar que se autorice una transacción con parámetros positivos válidos. |
| **Compra con precio cero** | Precio: $0.0, Cantidad: 20 | `false` | Exitoso | Rechazar transacciones donde el precio es cero. |
| **Compra con cantidad cero** | Precio: $10.0, Cantidad: 0 | `false` | Exitoso | Rechazar transacciones donde la cantidad de artículos es cero. |
| **Compra con precio negativo**| Precio: $-100.0, Cantidad: 5 | `false` | Exitoso | Validar el rechazo de precios negativos anómalos. |
| **Descuento inferior al umbral**| Subtotal: $4.50 | 0.0 | Exitoso | Comprobar que no se aplique descuento por debajo de $1,000. |
| **Descuento en el umbral exacto**| Subtotal: $1,000.0 | 100.0 | Exitoso | Validar la aplicación correcta del descuento exactamente en el límite. |
| **Descuento superior al umbral**| Subtotal: $1,100.0 | 110.0 | Exitoso | Verificar el cálculo porcentual correcto para compras mayores al umbral. |
| **Precio final sin descuento** | Subtotal/Precio: $50.0 | 50.0 | Exitoso | Asegurar que el precio final coincida con el subtotal cuando no aplica rebaja. |
| **Precio final con descuento** | Subtotal/Precio: $1,000.0 | 900.0 | Exitoso | Verificar la correcta aplicación de la resta del descuento sobre el total. |

---

## 4. Resultados de ejecución
* **Pruebas totales ejecutadas:** 4 métodos de prueba principales que agrupan un total de 12 afirmaciones (`assertions`).
* **Pruebas exitosas (Passed):** 12 (100%)
* **Pruebas fallidas (Failed):** 0 (0%)

Todas las aserciones implementadas pasaron satisfactoriamente bajo la tolerancia numérica definida (`0.001`), confirmando que la lógica actual cumple con los requerimientos esperados.

---

## 5. Errores que se pueden prevenir
1. **Aceptación de transacciones con montos negativos o nulos:** Sin la validación en `isValidPurchase`, el sistema podría procesar compras con precios o cantidades negativas, generando inconsistencias contables o saldos a favor erróneos para los usuarios.
2. **Aplicación incorrecta de políticas de descuentos por límites:** Errores comunes de operador (como usar estrictamente mayor `>` en lugar de mayor o igual `>=`) provocarían que clientes que alcanzan exactamente la meta de compra ($1,000) pierdan su beneficio de descuento, afectando la satisfacción del cliente y las reglas comerciales.

---

## 6. Conclusión
Las pruebas unitarias son un pilar indispensable para garantizar la confiabilidad en una aplicación de comercio electrónico. Al automatizar la verificación de funciones matemáticas y reglas de negocio complejas (como umbrales de descuento y validación de entradas), se minimiza drásticamente la probabilidad de introducir errores en producción. Esto asegura transacciones financieras estables, predecibles y seguras tanto para la plataforma como para los usuarios finales.