import org.junit.Assert.*
import org.junit.Test

class WalServiceTest {

    @Test
    fun createMessage() {
        val service = WalService
        service.createMessage(1, 2, "Hi. How are you?")
    }

    @Test
    fun editMessageTrue() {
        val service = WalService
        service.createMessage(1, 2, "Hi. How are you?")
        service.createMessage(2, 1, "Hi. How are you?")
        assertTrue(service.editMessage(1, Message(2, 2, 1, false, "Message")))
    }

    @Test
    fun editMessageFalseChat() {
        val service = WalService
        service.createMessage(1, 2, "Hi. How are you?")
        assertFalse(service.editMessage(100, Message(1, 1, 2, false, "Message")))
    }

    @Test
    fun editMessageFalse() {
        val service = WalService
        service.createMessage(1, 2, "Hi. How are you?")
        assertFalse(service.editMessage(1, Message(100, 1, 2, false, "Message")))
    }

    @Test
    fun deleteMessage() {
        val service = WalService
        service.createMessage(1, 2, "Hi. How are you?")
        assertTrue(service.deleteMessage(1, 1))
    }

    @Test
    fun deleteMessageTrue() {
        val service = WalService
        service.createMessage(1, 2, "Hi. How are you?")
        service.createMessage(2, 1, "Good.")
        assertTrue(service.deleteMessage(1, 1))
    }

    @Test
    fun deleteMessageFalseChat() {
        val service = WalService
        service.createMessage(1, 2, "Hi. How are you?")
        service.createMessage(2, 1, "Good.")
        assertFalse(service.deleteMessage(100, 1))
    }

    @Test
    fun deleteMessageFalse() {
        val service = WalService
        service.createMessage(1, 2, "Hi. How are you?")
        service.createMessage(2, 1, "Good.")
        assertFalse(service.deleteMessage(1, 100))
    }

    @Test
    fun getAllChatsUser() {
        val service = WalService
        service.createMessage(1, 2, "Hi. How are you?")
        service.createMessage(2, 1, "Good.")
        service.getAllChatsUser(1)
    }

    @Test
    fun getUnreadChatsCountTrue() {
        val service = WalService
        service.createMessage(1, 2, "Hi. How are you?")
        service.createMessage(2, 1, "Good.")
        service.createMessage(3, 1, "Hi. How are you?")
        service.createMessage(4, 1, "Good.")
        val n: Int = service.getUnreadChatsCount(1)
        assertTrue(n == 3)
    }

    @Test
    fun getUnreadChatsCountFalse() {
        val service = WalService
        service.createMessage(1, 2, "Hi. How are you?")
        service.createMessage(2, 1, "Good.")
        service.createMessage(3, 1, "Hi. How are you?")
        service.createMessage(4, 1, "Good.")
        val n: Int = service.getUnreadChatsCount(1)
        assertFalse(n == 100)
    }

    @Test
    fun getAllLastMessagesUser() {
        val service = WalService
        service.createMessage(1, 2, "Hi. How are you?")
        service.createMessage(2, 1, "Good.")
        service.createMessage(3, 1, "Hi. How are you?")
        service.createMessage(4, 1, "Good.")
        service.getAllLastMessagesUser(1)
    }

    @Test
    fun getMessages() {
        val service = WalService
        service.createMessage(1, 2, "Hi. How are you?")
        service.createMessage(2, 1, "Good.")
        service.createMessage(1, 2, "Hi. How are you?")
        service.createMessage(2, 1, "Good.")
        service.getMessages(1, 2, 1)
    }

    @Test
    fun getMessagesEmpty() {
        val service = WalService
        service.createMessage(1, 2, "Hi. How are you?")
        service.createMessage(2, 1, "Good.")
        service.createMessage(1, 2, "Hi. How are you?")
        service.createMessage(2, 1, "Good.")
        val p = service.getMessages(3, 5, 5)
        assertTrue(p.isEmpty())
    }

    @Test
    fun deleteChatTrue() {
        val service = WalService
        service.createMessage(1, 2, "Hi. How are you?")
        service.createMessage(2, 1, "Good.")
        service.createMessage(1, 2, "Hi. How are you?")
        service.createMessage(2, 1, "Good.")
        assertTrue(service.deleteChat(1))
    }

    @Test
    fun deleteChatFalse() {
        val service = WalService
        service.createMessage(1, 2, "Hi. How are you?")
        service.createMessage(2, 1, "Good.")
        assertFalse(service.deleteChat(100))
    }
}