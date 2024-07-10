package me.androidbox.presentation.coin_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.presentation.R
import me.androidbox.presentation.ui.theme.BusbyCoinsTheme

@Composable
fun InviteFriendCard(
    modifier: Modifier = Modifier,
    onInviteClicked: (webUrl: String) -> Unit
) {
    val weburl = "https://careers.lmwn.com"

    Card(
        shape = RoundedCornerShape(size = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFC5E6FF)
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp
        ),
        modifier = modifier
            .height(82.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 8.dp, bottom = 8.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(
                        color = Color.White)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(22.dp),
                    painter = painterResource(R.drawable.invite),
                    contentDescription = null
                )
            }

            val annotatedString = buildDescriptionAnnotatedString()

            ClickableText(
                overflow = TextOverflow.Ellipsis,
                text = annotatedString) { offSet ->
                annotatedString.getStringAnnotations(
                    tag = "clickable_text",
                    start = offSet,
                    end = offSet
                ).firstOrNull()?.let {
                    onInviteClicked(weburl)
                }
            }
        }
    }
}

@Composable
private fun buildDescriptionAnnotatedString(): AnnotatedString {
    val annotatedString = buildAnnotatedString {
        this.withStyle(
            SpanStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF000000)
            )
        ) {
            this.append("You can earn $10 when you invite a friend to by crypto.")
            this.append(" ")
        }

        this.pushStringAnnotation(
            tag = "clickable_text",
            annotation = "Invite",
        )

        this.withStyle(
            SpanStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF38A0FF)
            )
        ) {
            this.append(text = "Invite your friend")
        }
        pop()
    }
    return annotatedString
}

@Composable
@Preview
fun PreviewInviteFriendCard() {
    BusbyCoinsTheme {
        InviteFriendCard {

        }
    }
}