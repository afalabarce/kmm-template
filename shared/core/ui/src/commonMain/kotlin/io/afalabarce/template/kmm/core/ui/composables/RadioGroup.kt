package io.afalabarce.template.kmm.core.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
inline fun <reified T> RadioButtonGroup(
    modifier: Modifier = Modifier,
    crossinline radioButtonLabel: @Composable (T) -> Unit = { },
    crossinline radioButtonBody: @Composable (T) -> Unit = { },
    radioButtonValues: Array<T>,
    selectedValue: T?,
    borderStroke: BorderStroke? = null,
    dividerHeight: Dp = 4.dp,
    excludedValues: Array<T> = emptyArray(),
    radioButtonItemShape: Shape = MaterialTheme.shapes.medium,
    crossinline onCheckedChanged: (T) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        radioButtonValues
            .filter { notExcluded -> !excludedValues.any { excluded -> excluded == notExcluded } }
            .forEachIndexed { index, item ->
                if (index > 0)
                    Spacer(modifier = Modifier.size(dividerHeight))

                ConstraintLayout(
                    modifier = Modifier
                        .clip(radioButtonItemShape)
                        .border(borderStroke ?: BorderStroke(0.dp, Color.Unspecified), radioButtonItemShape)
                        .fillMaxWidth()
                        .clickable { onCheckedChanged(item) },
                ) {
                    val (radioButtonView, titleView, bodyView) = createRefs()
                    RadioButton(
                        modifier = Modifier.constrainAs(radioButtonView) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        },
                        selected = item == selectedValue,
                        onClick = { onCheckedChanged(item) }
                    )

                    Column(
                        modifier = Modifier.constrainAs(titleView) {
                            top.linkTo(radioButtonView.top)
                            bottom.linkTo(radioButtonView.bottom)
                            start.linkTo(radioButtonView.end, 2.dp)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        },
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        radioButtonLabel(item)
                    }

                    Column(
                        modifier = Modifier.constrainAs(bodyView) {
                            top.linkTo(titleView.bottom)
                            start.linkTo(titleView.start)
                            end.linkTo(parent.end, 4.dp)
                            width = Dimension.fillToConstraints
                        },
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {
                        radioButtonBody(item)
                    }
                }
            }
    }
}
