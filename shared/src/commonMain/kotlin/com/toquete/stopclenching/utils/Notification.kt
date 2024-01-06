package com.toquete.stopclenching.utils

import com.toquete.stopclenching.Resources
import dev.icerock.moko.resources.ImageResource

data class Notification private constructor(
    val smallIcon: ImageResource,
    val title: String?,
    val text: String?,
    val priority: Priority,
    val category: String?
) {

    class Builder {
        private var smallIcon: ImageResource = Resources.images.notification_important
        private var title: String? = null
        private var text: String? = null
        private var priority: Priority = Priority.DEFAULT
        private var category: String? = null

        fun setSmallIcon(smallIcon: ImageResource) = apply { this.smallIcon = smallIcon }
        fun setTitle(title: String) = apply { this.title = title }
        fun setText(text: String) = apply { this.text = text }
        fun setPriority(priority: Priority) = apply { this.priority = priority }
        fun setCategory(category: String) = apply { this.category = category }

        fun build(): Notification {
            return Notification(
                smallIcon = smallIcon,
                title = title,
                text = text,
                priority = priority,
                category = category
            )
        }
    }

    enum class Priority {
        MAX,
        HIGH,
        DEFAULT,
        LOW
    }
}
