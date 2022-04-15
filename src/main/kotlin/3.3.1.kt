fun main() {
    
}

object WalService {
    var chats: MutableList<Chat> = ArrayList<Chat>()

    fun createMessage(userOutId: Int, userInId: Int, text: String, messageID: Int = 0, isRead: Boolean = false) {
        var chat: Chat? = chats.find {
            !it.messages.filter {
                it.userOutId == userOutId && it.userInId == userInId ||
                        it.userOutId == userInId && it.userInId == userOutId
            }.isEmpty()
        }
        if (chat != null) {
            deleteChat(chat.chatId)
            chat.messages.add(
                Message(
                    chat.messages.last().messageId + 1, userOutId, userInId,
                    false, text
                )
            )
            chat.countMessage++
            chats.add(chat.copy(chatId = 1))
        } else {
            var mess: MutableList<Message> = ArrayList<Message>()
            mess.add(
                Message(
                    1, userOutId, userInId,
                    false, text
                )
            )
            createChat(Chat(0, mess, 0, 1, 1, 1))
        }
    }

    fun editMessage(chatId: Int, message: Message): Boolean {
        if (deleteMessage(chatId, message.messageId)) {
            createMessage(message.userOutId, message.userInId, message.text, message.messageId)
            return true
        }
        return false
    }

    fun deleteMessage(chatId: Int, messageId: Int): Boolean {
        var chat: Chat? = chats.find { it.chatId == chatId }
        if (chat == null) {
            return false
        }
        val mes: Message? = chat.messages.find { it.messageId == messageId }
        if (mes == null) {
            return false
        }
        if (chat.messages.size == 1) {
            deleteChat(chatId)
            return true
        }
        chat.messages.remove(mes)
        return true
    }

    fun getAllChatsUser(userId: Int): List<Chat> {
        return chats.filterNot { it.messages.filter { it.userInId == userId || it.userOutId == userId }.isEmpty() }
    }

    fun getUnreadChatsCount(userId: Int): Int {
        if (chats.isEmpty()) {
            return 0
        }
        val unreadChats: List<Chat>? = chats.filterNot {
            it.messages.filter {
                it.userInId == userId &&
                        it.isRead == false
            }.isEmpty()
        }
        if (unreadChats != null) {
            return unreadChats.size;
        }
        return 0
    }

    fun getAllLastMessagesUser(userId: Int): List<Message> {
        val chs: List<Chat> = getAllChatsUser(userId)
        var mess: MutableList<Message> = ArrayList<Message>()
        for (c in chs) {
            mess.add(c.messages.last())
        }
        return mess
    }

    fun getMessages(userId: Int, lastId: Int, count: Int): List<Message> {
        var mess: MutableList<Message>? = ArrayList()
        mess = getAllLastMessagesUser(userId).filter { it.messageId >= lastId &&  it.messageId <= lastId + count} as MutableList
        if (mess != null) {
            editIsRead (userId, mess);
            return mess
        }
        else {
            return emptyList()
        }
    }

    fun deleteChat(id: Int): Boolean {
        val oldChats = chats;
        chats = chats.filter { it.chatId != id } as MutableList<Chat>
        if (chats.size < oldChats.size) {
            return true
        } else {
            return false
        }
    }

    private fun createChat(chat: Chat) {
        if (chats.isEmpty()) {
            chats.add(chat.copy(chatId = 1))
        } else {
            var id = chats.last().chatId
            chats.add(chat.copy(chatId = ++id))
        }
    }

    private fun editIsRead (userId: Int, mess: List<Message>): Boolean {
        if (mess.isEmpty()) {
            return false
        }
        for (ch in chats) {
            for (ms in ch.messages) {
                for (p in mess) {
                    if (ms.messageId == p.messageId) {
                        ms.isRead = true
                    }
                }
            }
        }
        return true
    }
}

data class Chat(
    val chatId: Int,
    var messages: MutableList<Message>,
    val adminId: Int,
    var countMessage: Int = 0,
    var lastMessageId: Int = 0,
    var countNoRed: Int
)

data class Message(
    val messageId: Int,
    val userOutId: Int,
    val userInId: Int,
    var isRead: Boolean,
    val text: String
)