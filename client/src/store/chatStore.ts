import { create } from "zustand"
import { persist } from "zustand/middleware"
import type { ChatStore, Chat } from "../types/chat"

export const useChatStore = create<ChatStore>()(
  persist(
    (set, get) => ({
      chats: [],
      currentChatId: null,
      sidebarOpen: true,
      isLoading: false,

      addChat: (chat: Chat) => {
        set((state) => ({
          chats: [chat, ...state.chats],
          currentChatId: chat.id,
        }))
      },

      updateChat: (chatId: string, updates: Partial<Chat>) => {
        set((state) => ({
          chats: state.chats.map((chat) => (chat.id === chatId ? { ...chat, ...updates } : chat)),
        }))
      },

      deleteChat: (chatId: string) => {
        set((state) => {
          const newChats = state.chats.filter((chat) => chat.id !== chatId)
          const newCurrentChatId =
            state.currentChatId === chatId ? (newChats.length > 0 ? newChats[0].id : null) : state.currentChatId

          return {
            chats: newChats,
            currentChatId: newCurrentChatId,
          }
        })
      },

      setCurrentChat: (chatId: string | null) => {
        set({ currentChatId: chatId })
      },

      toggleSidebar: () => {
        set((state) => ({ sidebarOpen: !state.sidebarOpen }))
      },

      setSidebarOpen: (open: boolean) => {
        set({ sidebarOpen: open })
      },

      setLoading: (loading: boolean) => {
        set({ isLoading: loading })
      },
    }),
    {
      name: "chat-storage",
    },
  ),
)
